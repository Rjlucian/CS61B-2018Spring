import edu.princeton.cs.algs4.Picture;

import java.awt.Color;
import java.util.Arrays;

/**
 * @author Ruoji Wang
 */
public class SeamCarver {

    private Picture picture;
    private Color[][] colors;
    private double[][] energies;

    public SeamCarver(Picture picture) {
        this.picture = picture;
        initColors();
        initEnergies();
    }

    public Picture picture() {
        return picture;
    }

    /**
     * @return picture's width
     */
    public int width() {
        return picture.width();
    }

    /**
     * @return picture's height
     */
    public int height() {
        return picture.height();
    }

    /**
     * @param x pixel's column
     * @param y pixel's row
     * @return energy of pixel at column x, row y
     */
    public double energy(int x, int y) {
        validateX(x);
        validateY(y);
        return energies[y][x];
    }

    /**
     * find a horizontal seam from picture
     * this is a typical dynamic programming problem
     * @return an array consists of row index of all the pixels in the seam
     */
    public int[] findVerticalSeam() {
        double[][] dp = getDPArr();
        int[][] pre = new int[height()][width()];
        Arrays.fill(pre[0], -1);
        for (int y = 1; y < height(); y++) {
            for (int x = 0; x < width(); x++) {
                int curPre = help(dp[y - 1], x);
                dp[y][x] = dp[y - 1][curPre] + energies[y][x];
                pre[y][x] = curPre;
            }
        }
        int minIndex = findMin(dp[height() - 1]);
        return getSeamFromPre(pre, minIndex);
    }

    private int help(double[] arr, int index) {
        if (index == 0) {
            if (arr[index] < arr[index + 1]) {
                return index;
            } else {
                return index + 1;
            }
        } else if (index == arr.length - 1) {
            if (arr[index - 1] < arr[index]) {
                return index - 1;
            } else {
                return index;
            }
        } else {
            double min = Math.min(Math.min(arr[index], arr[index - 1]), arr[index + 1]);
            if (min == arr[index - 1]) {
                return index - 1;
            } else if (min == arr[index]) {
                return index;
            } else {
                return index + 1;
            }
        }
    }

    /**
     * find a vertical seam from picture
     * @return an array consists of column index of all the pixels in the seam
     */
    public int[] findHorizontalSeam() {
        Picture transposing = getTransposingPicture(picture);
        SeamCarver client = new SeamCarver(transposing);
        int[] seam = client.findVerticalSeam();
        for (int i = 0; i < seam.length; i++) {
            seam[i] = picture.height() - 1 - seam[i];
        }
        return seam;
    }

    public void removeHorizontalSeam(int[] seam) {
        checkHorizontalSeam(seam);
        SeamRemover.removeHorizontalSeam(picture, seam);
    }

    public void removeVerticalSeam(int[] seam) {
        checkVerticalSeam(seam);
        SeamRemover.removeVerticalSeam(picture, seam);
    }

    private void checkVerticalSeam(int[] seam) {
        if (seam.length != this.height()) {
            throw new IllegalArgumentException();
        }
        for (int i = 1; i < seam.length; i++) {
            int tmp = seam[i] - seam[i - 1];
            if (tmp < -1 || tmp > 1) {
                throw new IllegalArgumentException();
            }
        }
    }

    private void checkHorizontalSeam(int[] seam) {
        if (seam.length != this.width()) {
            throw new IllegalArgumentException();
        }
        for (int i = 1; i < seam.length; i++) {
            int tmp = seam[i] - seam[i - 1];
            if (tmp < -1 || tmp > 1) {
                throw new IllegalArgumentException();
            }
        }
    }



    private void validateX(int x) {
        if (x < 0 || x > this.width() - 1) {
            String str = "x equals to " + x + ", "
                    + "but it should be between " + 0 + " and " + (this.width() - 1);
            throw new IndexOutOfBoundsException(str);
        }
    }

    private void validateY(int y) {
        if (y < 0 || y > this.height() - 1) {
            String str = "y equals to " + y + ", "
                    + "but it should be between " + 0 + " and " + (this.height() - 1);
            throw new IndexOutOfBoundsException(str);
        }
    }

    private void initColors() {
        colors = new Color[height()][width()];
        for (int y = 0; y < height(); y++) {
            for (int x = 0; x < width(); x++) {
                colors[y][x] = picture.get(x, y);
            }
        }
    }

    private void initEnergies() {
        energies = new double[height()][width()];
        for (int y = 0; y < height(); y++) {
            for (int x = 0; x < width(); x++) {
                energies[y][x] = calculateEnergyX(x, y) + calculateEnergyY(x, y);
            }
        }
    }

    /**
     * calculate the x-gradient energy of pixel(x, y)
     * @param x pixel(x, y)'s column
     * @param y pixel(x, y)'s row
     * @return square of pixel(x, y)'s x-gradient energy
     */
    private double calculateEnergyX(int x, int y) {
        int left = getLeftColumn(x);
        int right = getRightColumn(x);
        double redDiff = colors[y][left].getRed() - colors[y][right].getRed();
        double greenDiff = colors[y][left].getGreen() - colors[y][right].getGreen();
        double blueDiff = colors[y][left].getBlue() - colors[y][right].getBlue();
        return Math.pow(redDiff, 2) + Math.pow(greenDiff, 2) + Math.pow(blueDiff, 2);
    }

    /**
     * calculate the x-gradient energy of pixel(x, y)
     * @param x pixel(x, y)'s column
     * @param y pixel(x, y)'s row
     * @return square of pixel(x, y)'s y-gradient energy
     */
    private double calculateEnergyY(int x, int y) {
        int up = getUpRow(y);
        int down = getDownRow(y);
        double redDiff = colors[up][x].getRed() - colors[down][x].getRed();
        double greenDiff = colors[up][x].getGreen() - colors[down][x].getGreen();
        double blueDiff = colors[up][x].getBlue() - colors[down][x].getBlue();
        return Math.pow(redDiff, 2) + Math.pow(greenDiff, 2) + Math.pow(blueDiff, 2);
    }

    /**
     * @param x pixel(x, y)'s column index
     * @return the pixel's left one's column index, or width - 1 if x equals to 0
     */
    private int getLeftColumn(int x) {
        if (x == 0) {
            return width() - 1;
        } else {
            return x - 1;
        }
    }

    /**
     * @param x pixel(x, y)'s column index
     * @return the pixel's right one's column index, or 0 if x equals to width - 1
     */
    private int getRightColumn(int x) {
        if (x == width() - 1) {
            return 0;
        } else {
            return x + 1;
        }
    }

    /**
     * @param y pixel(x, y)'s row index
     * @return the pixel's up one's row index, or height - 1 if y equals to 0;
     */
    private int getUpRow(int y) {
        if (y == 0) {
            return height() - 1;
        } else {
            return y - 1;
        }
    }

    /**
     * @param y pixel(x, y)'s row index
     * @return the pixel's down one's row index, or 0 if y equals to height - 1
     */
    private int getDownRow(int y) {
        if (y == height() - 1) {
            return 0;
        } else {
            return y + 1;
        }
    }

    /** get an array to solve dp problem in this class and initialize it
     * @return an array used to solve dp problem
     */
    private double[][] getDPArr() {
        double[][] dp = new double[height()][width()];
        System.arraycopy(energies[0], 0, dp[0], 0, width());
        for (int y = 1; y < height(); y++) {
            for (int x = 0; x < width(); x++) {
                dp[y][x] = 0;
            }
        }
        return dp;
    }

    private int findMin(double[] arr) {
        double min = Double.MAX_VALUE;
        int index = -1;
        for (int i = 0; i < arr.length; i++) {
            if (min > arr[i]) {
                min = arr[i];
                index =  i;
            }
        }
        return index;
    }

    private int[] getSeamFromPre(int[][] pre, int minIndex) {
        int[] seam = new int[height()];
        seam[seam.length - 1] = minIndex;
        for (int y = height() - 1; y >= 1 && minIndex != -1; y--) {
            minIndex = pre[y][minIndex];
            seam[y - 1] = minIndex;
        }
        return seam;
    }

    private static Picture getTransposingPicture(Picture picture) {
        Picture newp = new Picture(picture.height(), picture.width());
        for (int y = 0; y < picture.height(); y++) {
            for (int x = 0; x < picture.width(); x++) {
                newp.set(picture.height() - y - 1, x, picture.get(x, y));
            }
        }
        return newp;
    }

}
