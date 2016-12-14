package r33.util;

import r33.ai.Field;

/**
 * Created by Ejdems on 14/12/2016.
 */
public class GridPrinter {
    public static void printGrid(int[][] scannedGrid, Field field) {
        for (int y = field.getY()-1; y >= 0; y--) {
            System.out.print(y + " ");
            for (int x = 0; x < field.getX(); x++) {
                if (scannedGrid[x][y] < 10) {
                    System.out.print(" |" + scannedGrid[x][y] + " | ");
                } else {
                    System.out.print(" |" + scannedGrid[x][y] + "| ");
                }
            }
            System.out.println("");
        }
        System.out.println("-----------------------------------------------------------------------------");
        System.out.print("   ");
        for (int x = 0; x < field.getX(); x++) {
            System.out.print(" "+x+"    ");
        }
        System.out.println("\n----------------------------------------\n");
    }
}
