package org.nexu.spaceinvader.utils;

import org.nexu.spaceinvader.domain.Point;

import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: cyril
 * Date: 4/28/13
 * Time: 3:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class MathUtil {


    /**
     * Complexity O(N point * M nbValueToTrack)
     *
     * @param originPoint
     * @param nbValueToTrack
     * @param set
     * @return
     */
    public static Point[] isCloser(Point originPoint, int nbValueToTrack, Point... set) {
        Point[] rPoint = new Point[nbValueToTrack];
        int[] values = new int[nbValueToTrack];
        Arrays.fill(values, Integer.MAX_VALUE);

        for(int i = 0; i < set.length; i++) {
            Point currentPoint = set[i];
            int value = originPoint.compareTo(currentPoint);

            for(int j = 0; j < nbValueToTrack; j++) {
                if(values[j] == Integer.MAX_VALUE) {
                    values[j] = value;
                    rPoint[j] = currentPoint;
                    break;
                }

                if(values[j] > value) {
                    while(values[j] != Integer.MAX_VALUE) {
                          unsafeRightShift(values, rPoint, j, nbValueToTrack);
                    }
                    values[j] = value;
                    rPoint[j] = currentPoint;
                }
            }

        }

         return rPoint;
    }


    private static <T> void unsafeRightShift(T[] array, int from, int end)  {
        // Validation
        if(from < 0 || from > array.length || end < from || end > array.length) {
            return;
        }

        for(int i = end; i >= from; i--) {
             if(i + 1 < array.length) {
                 array[i+1] = array[i];
             }
        }
    }

    private static void unsafeRightShift(int[] arrayA, Point[] arrayB, int from, int end)  {
        if(arrayB.length < arrayA.length) {return;}
        // Validation
        if(from < 0 || from > arrayA.length || end < from || end > arrayA.length) {
            return;
        }

        for(int i = end; i >= from; i--) {
            if(i + 1 < arrayA.length) {
                arrayA[i+1] = arrayA[i];
                arrayB[i+1] = arrayB[i];
            }
        }
    }

    private static <T> void swap(T[] array, int indexSrc, int indexDst) {
        T tmpValue = array[indexSrc];
        array[indexSrc] = array[indexDst];
        array[indexDst] = tmpValue;
    }



    /**
     * (Xp2 - Xp1)^2 + (Yp2 - Yp1)^2
     * @param pPointA
     * @param pPointB
     * @return
     */
    private static double getDistanceBetweenPoints(Point pPointA, Point pPointB) {
        double toCompute = Math.pow((double)pPointA.getX() - pPointB.getX(), 2d) + Math.pow((double)pPointA.getY() - pPointB.getY(), 2d);
        return Math.sqrt(toCompute);
    }

    public static boolean isPointInsideCircle(int radius, Point center, Point testPoint) {
        return radius > getDistanceBetweenPoints(center, testPoint);
    }
}

