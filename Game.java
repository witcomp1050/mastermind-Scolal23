package mastermind;

public class Game {

    public static final String[] colors = {"B", "G", "O", "P", "R", "Y"};

    public int[] generateSecretCode() {
        int [] result= new int [4];
        int num;
        int pegs=4;
        for(int i=0; i<pegs; i++)
        {
            num=(int)(Math.random()*6);;
            result[i]=num;
            System.out.println(colors[num]);
        }
        return result;
    }


    public int[] getDots(int[] code, int[] nums) {

        /**
         * Cloning the arrays so it dose not change on the GUI program so the program can
         * use the arrays it later on in the program
         */
        int[] cloneCode = cloneIntArray(code);
        int[] cloneNums = cloneIntArray(nums);

        int blackDots = 0;
        for (int i=0; i < cloneCode.length; i++) {
            if (cloneCode[i]==cloneNums[i]) {
                blackDots++;
                cloneNums[i]=-1;
                cloneCode[i]=-1;
            }
        }

        int whiteDots = 0;
        for (int i=0; i < cloneCode.length; i++) {
            if (cloneCode[i]==-1) {
                continue;
            }
            for (int j=0;j < cloneCode.length; j++) {
                if (cloneNums[j]==-1) {
                    continue;
                }
                if (cloneCode[i]==cloneNums[j]) {
                    whiteDots++;
                    cloneCode[i]=-1;
                    cloneNums[j]=-1;
                }
            }
        }

        /**
         *returing an array so we can return both white dots and black dots
         */
        int[] dots = new int[2];
        dots[0] = blackDots;
        dots[1] = whiteDots;

        return dots;
    }

    /** Able to clone an static int Array and return a clone of it
     *This allows the program to remove elements of the array without
     * changing the originals
     */
    public int[] cloneIntArray(int [] original)
    {
        int [] clone = new int [original.length];
        for (int i = 0; i < original.length; i++)
        {
            clone[i] = original[i];
        }
        return clone;
    }
}