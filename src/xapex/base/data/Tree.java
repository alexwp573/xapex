package xapex.base.data;

import xapex.base.Id;
import xapex.base.center.environment.logs.Debugger;

import java.util.Arrays;

public class Tree {

    public static class BranchDoesNotExist extends Exception {
        public final int INDEX;
        BranchDoesNotExist(String msg, int index){super(msg); this.INDEX = index;}
    }
    public static class LeafDoesNotExist extends Exception {
        LeafDoesNotExist(String msg){super(msg);}
    }
    public static class BranchAlreadyCoredException extends Exception {
        BranchAlreadyCoredException(String msg){super(msg);}
    }

    public static class Path {

        public final List<String> PATH;
        public final int STEPS;
        public final String TARGET;

        private Path(List<String> path, String target){
            this.PATH = path;
            this.TARGET = target;
            this.STEPS = this.PATH.count();
        }
        public Path(String path){
            String[] split = path.split("/");
            this.PATH = new List<>();
            if(split[split.length - 1].charAt(0) == ':') {
                this.TARGET = split[split.length - 1];
                split = Arrays.copyOfRange(split, 0, split.length - 1);
            }
            else{
                this.TARGET = null;
            }
            for(String s : split)
                this.PATH.add(s);
            this.STEPS = this.PATH.count();
        }

        public Path steps(int stepsCount){
            List<String> temp = new List<>();
            for(String s : this.PATH){
                if(stepsCount-- > 0) {
                    temp.add(s);
                }
                else{break;}
            }
            return new Path(temp, this.TARGET);
        }
        public Path step(){
            List<String> temp = new List<>();
            boolean firstIgnored = false;
            for(String s : this.PATH){
                if(!firstIgnored){
                    firstIgnored = true;
                }
                else{
                    temp.add(s);
                }
            }
            return new Path(temp, this.TARGET);
        }
        public String next(){
            return this.PATH.check(0);
        }

        public String toString(){
            StringBuilder stringBuilder = new StringBuilder();
            for(String s : this.PATH)
                stringBuilder.append(s).append("/");
            stringBuilder.append(this.TARGET);
            return stringBuilder.toString();
        }
    }

    public static class Branch {

        public final Id ID = new Id("ID");
        public final String NAME;
        protected Branch CORE = null;
            public Branch core(){return this.CORE;}
            public void addCore(Branch core) throws BranchAlreadyCoredException {
                if(this.CORE == null) this.CORE = core;
                else throw new BranchAlreadyCoredException("The branch " + this.NAME + " is already cored by branch: " + this.CORE.NAME);
            }
        public final List<Branch> BRANCHES = new List<>();
            public List<Branch> branches(){return this.BRANCHES;}
        protected final List<SignedElement> DATA = new List<>();
            public List<SignedElement> data(){return this.DATA;}

        protected Branch(String name){this.NAME = name;}
        public static Branch create(String name){return new Branch(name);}

        public Branch grow(Branch branch) throws BranchAlreadyCoredException {
            this.BRANCHES.add(branch);
            branch.addCore(this);
            return branch;
        }
        public Branch branch(String branchName) throws BranchDoesNotExist {
            int i = 0;
            for(Branch b : this.BRANCHES){
                if(b.NAME.compareTo(branchName) == 0)
                    return b;
                i++;
            }
            throw new BranchDoesNotExist("Branch " + branchName + " does not exist", i);
        }

        public void grow(Object obj, String name){
            this.DATA.add(new SignedElement(name, obj));
        }
        public Object leaf(String name) throws LeafDoesNotExist {
            for(SignedElement se : this.DATA){
                if(se.name().compareTo(name) == 0){
                    return se.element();
                }
            }
            throw new LeafDoesNotExist("Data " + name + " does not exist");
        }

        public String toString(){
            StringBuilder stringBuilder = new StringBuilder(this.NAME + " ( ");
            for(Branch b : this.BRANCHES){
                stringBuilder.append(b.toString());
            }
            stringBuilder.append(" ) ");
            return stringBuilder.toString();
        }
    }
    public static class LinkedBranch extends Branch{

        protected final List<Branch> LINKS = new List<>();
            public void addCore(Branch core){
                try{super.addCore(core);}
                catch(BranchAlreadyCoredException bace){
                    this.LINKS.add(core);
                }

            }

        protected LinkedBranch(String name) {
            super(name);
        }
        public static LinkedBranch create(String name){return new LinkedBranch(name);}
    }

    public final String NAME;
    public final Id ID = new Id("ID");
    public final List<Branch> BRANCHES = new List<>();

    public Tree(String name){this.NAME = name;}



    public Branch grow(String branchPath){
        return this.grow(new Path(branchPath));
    }
    public Branch grow(Path branchPath){
        int missing = this.missing(branchPath);
        Debugger.db("grow():branchPath.STEPS: " + branchPath.STEPS);
        Debugger.db("missing: " + missing);
        Path existingPath = branchPath.steps(branchPath.STEPS - missing);
        for(int i = 0; i < existingPath.STEPS; i++){
            branchPath = branchPath.step();
        }
        Debugger.db("---HERE172");


        try {
            Branch tempB = null;
            if(existingPath.STEPS > 0)
                tempB = this.branch(existingPath);
            Debugger.db("---HERE175");
            for(int i = 0; i < branchPath.STEPS;){
                Debugger.db("Branch grow iteration rep: " + i + " on branch: " + (tempB == null ? "null" : tempB.NAME));
                Debugger.db(existingPath.toString());
                Debugger.db(branchPath.toString());
                Debugger.db("branchPath.STEPS: " + branchPath.STEPS);
                if(tempB == null){
                    tempB = Branch.create(branchPath.next());
                    this.BRANCHES.add(tempB);
                }
                else{
                    tempB = tempB.grow(Branch.create(branchPath.next()));
                }
                branchPath = branchPath.step();
            }
            return tempB;
        }
        catch (BranchDoesNotExist | BranchAlreadyCoredException e) {
            Debugger.db(e instanceof BranchDoesNotExist ? "BranchDoesNotExists caught" : "BranchAlreadyCoredException caught");}



        return null;
    }
    public int missing(String branchPath){
        return this.missing(new Path(branchPath));
    }
    public int missing(Path branchPath){



        Debugger.db("missing():branchPath.toString(): " + branchPath.toString());
        Branch tempB;
        try {tempB = this.branch(branchPath.next());}
        catch (BranchDoesNotExist branchDoesNotExist) {return branchPath.STEPS;}
        Debugger.db(tempB == null ? "null" : tempB.NAME);
        Debugger.db("missing():branchPath.toString(): " + branchPath.toString());
        branchPath = branchPath.step();
        Debugger.db("int missing(): branchPath.STEPS: " + branchPath.STEPS);
        Debugger.db("missing():branchPath.toString(): " + branchPath.toString());
        for(int i = 0; i <= branchPath.STEPS; i++){



            Debugger.db("iteration: " + i + " next: " + branchPath.next());
            for(Branch b : tempB.BRANCHES) Debugger.db("FOR-EACH-LOOP on tempB.BRANCHES iteration with: " + b.NAME);
            try{
                tempB = tempB.branch(branchPath.next());
                Debugger.db(tempB.NAME);
            }
            catch(BranchDoesNotExist branchDoesNotExist){
                Debugger.db("CATCHED!!! STEPS: " + (branchPath.STEPS) + " i: " + i);
                Debugger.db("missing to return: " + (branchPath.STEPS - i));
                return branchPath.STEPS - i;
            }
            branchPath = branchPath.step();



        }
        return 0;
    }
    public Branch branch(String branchPath) throws BranchDoesNotExist {
        return this.branch(new Path(branchPath));
    }
    public Branch branch(Path branchPath) throws BranchDoesNotExist {
        Branch tempBranch = null;
        for (String s : branchPath.PATH) {
            if (tempBranch == null) {
                for (Branch b : this.BRANCHES)
                    if (b.NAME.compareTo(s) == 0)
                        tempBranch = b;
                if (tempBranch == null) throw new BranchDoesNotExist("Branch " + s + " does not exist.", 0);
            } else {
                tempBranch = tempBranch.branch(s);
            }
        }
        return tempBranch;
    }

    public void grow(String leafPath, Object leaf){
        this.grow(new Path(leafPath), leaf);
    }
    public void grow(Path leafPath, Object leaf){
        this.grow(leafPath).grow(leaf, leafPath.TARGET);
    }
    public boolean exists(String leafPath){
        return this.exists(new Path(leafPath));
    }
    public boolean exists(Path leafPath){
        try{Branch tempB = this.branch(leafPath);
            tempB.leaf(leafPath.TARGET);
            return true;}
        catch(BranchDoesNotExist | LeafDoesNotExist e){return false;}
    }
    public Object leaf(String leafPath) throws BranchDoesNotExist, LeafDoesNotExist {
        return this.leaf(new Path(leafPath));
    }
    public Object leaf(Path leafPath) throws BranchDoesNotExist, LeafDoesNotExist {
        return this.branch(leafPath).leaf(leafPath.TARGET);
    }

    public void remove(String branchPath){
        this.remove(new Path(branchPath));
    }
    public void remove(Path branchPath){

    }

    public String toString(){
        StringBuilder stringBuilder = new StringBuilder(this.NAME + " ( ");
        for(Branch b : this.BRANCHES){
            stringBuilder.append(b.toString());
        }
        return stringBuilder.append(" ) ").toString();
    }
}
