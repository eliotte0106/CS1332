import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.List;
import java.util.LinkedList;

/**
 * Your implementation of various sorting algorithms.
 *
 * @author Jihong Park
 * @version 1.0
 * @userid jpark3027
 * @GTID 903665760
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class Sorting {

    /**
     * Implement selection sort.
     *
     * It should be:
     * in-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n^2)
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void selectionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("array or comparator is null");
        }
        for (int i = 0; i < arr.length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (comparator.compare(arr[minIndex], arr[j]) > 0) {
                    minIndex = j;
                }
            }
            T temp = arr[i];
            arr[i] = arr[minIndex];
            arr[minIndex] = temp;
        }
    }

    /**
     * Implement cocktail sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * NOTE: See pdf for last swapped optimization for cocktail sort. You
     * MUST implement cocktail sort with this optimization
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void cocktailSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("array or comparator is null");
        }
        int lastSwapped = 0;
        int left = 0;
        int right = arr.length - 1;
        boolean swapped = true;
        while (swapped) {
            swapped = false;
            for (int i = lastSwapped; i < right; i++) {
                if (comparator.compare(arr[i], arr[i + 1]) > 0) {
                    swap(i, i + 1, arr);
                    lastSwapped = i;
                    swapped = true;
                }
            }
            right = lastSwapped;
            if (!swapped) {
                break;
            } else {
                swapped = false;
                for (int i = lastSwapped; i > left; i--) {
                    if (comparator.compare(arr[i], arr[i - 1]) < 0) {
                        swap(i, i - 1, arr);
                        lastSwapped = i;
                        swapped = true;
                    }
                }
                left = lastSwapped;
            }
        }
    }

    /**
     * Implement merge sort.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * You can create more arrays to run merge sort, but at the end, everything
     * should be merged back into the original T[] which was passed in.
     *
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     *
     * Hint: If two data are equal when merging, think about which subarray
     * you should pull from first
     *
     * @param <T>        data type to sort
     * @param arr        the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @throws IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("array or comparator is null");
        }
        if (arr.length > 1) {
            T[] left = (T[]) new Object[arr.length / 2];
            T[] right = (T[]) new Object[arr.length - left.length];
            for (int i = 0; i < left.length; i++) {
                left[i] = arr[i];
            }
            for (int i = 0; i < right.length; i++) {
                right[i] = arr[i + left.length];
            }
            mergeSort(left, comparator);
            mergeSort(right, comparator);
            merge(arr, comparator, left, right);
        }
    }

    /**
     * merge method
     * @param arr array to be sorted
     * @param comparator to compare values
     * @param left left array
     * @param right right array
     * @param <T> data type
     */
    private static <T> void merge(T[] arr, Comparator<T> comparator, T[] left, T[] right) {
        int i = 0;
        int j = 0;
        for (int k = 0; k < arr.length; k++) {
            if (j >= right.length || i < left.length && comparator.compare(left[i], right[j]) <= 0) {
                arr[k] = left[i];
                i++;
            } else {
                arr[k] = right[j];
                j++;
            }
        }
    }
    /**
     * Implement quick sort.
     *
     * Use the provided random object to select your pivots. For example if you
     * need a pivot between a (inclusive) and b (exclusive) where b > a, use
     * the following code:
     *
     * int pivotIndex = rand.nextInt(b - a) + a;
     *
     * If your recursion uses an inclusive b instead of an exclusive one,
     * the formula changes by adding 1 to the nextInt() call:
     *
     * int pivotIndex = rand.nextInt(b - a + 1) + a;
     *
     * It should be:
     * in-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not receive
     * credit if you do not use the one we have taught you!
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @param rand       the Random object used to select pivots
     * @throws IllegalArgumentException if the array or comparator or
     *                                            rand is null
     */
    public static <T> void quickSort(T[] arr, Comparator<T> comparator,
                                     Random rand) {
        if (arr == null || comparator == null || rand == null) {
            throw new IllegalArgumentException("array or comparator is null");
        }
        qsHelper(arr, 0, arr.length - 1, comparator, rand);
    }

    /**
     * quick sort helper method (Hoare partition)
     * @param arr array to be sorted
     * @param start start index
     * @param end end index
     * @param c to compare values
     * @param rand random for random pivot
     * @param <T> data type
     */
    private static <T> void qsHelper(T[] arr, int start, int end, Comparator<T> c, Random rand) {
        if (start >= end) {
            return;
        }
        int pivot = rand.nextInt(end - start + 1) + start;
        int left = start + 1;
        int right = end;
        T p = arr[pivot];
        swap(pivot, start, arr);
        while (left <= right) {
            while (left <= right && c.compare(arr[left], p) <= 0) {
                left++;
            }
            while (left <= right && c.compare(arr[right], p) >= 0) {
                right--;
            }
            if (left <= right) {
                swap(left, right, arr);
                left++;
                right--;
            }
        }
        swap(right, start, arr);
        qsHelper(arr, start, right - 1, c, rand);
        qsHelper(arr, right + 1, end, c, rand);
    }

    /**
     * swap method
     * @param a a index
     * @param b b index
     * @param arr array
     * @param <T> data type
     */
    private static <T> void swap(int a, int b, T[] arr) {
        T tmp = arr[a];
        arr[a] = arr[b];
        arr[b] = tmp;
    }
    /**
     * Implement LSD (least significant digit) radix sort.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     *
     * Remember you CANNOT convert the ints to strings at any point in your
     * code! Doing so may result in a 0 for the implementation.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(kn)
     *
     * And a best case running time of:
     * O(kn)
     *
     * You are allowed to make an initial O(n) passthrough of the array to
     * determine the number of iterations you need. The number of iterations
     * can be determined using the number with the largest magnitude.
     *
     * At no point should you find yourself needing a way to exponentiate a
     * number; any such method would be non-O(1). Think about how how you can
     * get each power of BASE naturally and efficiently as the algorithm
     * progresses through each digit.
     *
     * Refer to the PDF for more information on LSD Radix Sort.
     *
     * You may use ArrayList or LinkedList if you wish, but it may only be
     * used inside radix sort and any radix sort helpers. Do NOT use these
     * classes with other sorts. However, be sure the List implementation you
     * choose allows for stability while being as efficient as possible.
     *
     * Do NOT use anything from the Math class except Math.abs().
     *
     * @param arr the array to be sorted
     * @throws IllegalArgumentException if the array is null
     */
    public static void lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("Array can't be null");
        }
        LinkedList<Integer>[] buckets = new LinkedList[19];
        for (int i = 0; i < 19; i++) {
            buckets[i] = new LinkedList<>();
        }
        int mod = 10;
        int div = 1;
        boolean cont = true;
        while (cont) {
            cont = false;
            for (int num : arr) {
                int bucket = num / div;
                if (bucket / 10 != 0) {
                    cont = true;
                }
                if (buckets[bucket % mod + 9] == null) {
                    buckets[bucket % mod + 9] = new LinkedList<>();
                }
                buckets[bucket % mod + 9].add(num);
            }
            int arrIdx = 0;
            for (LinkedList<Integer> bucket : buckets) {
                if (bucket != null) {
                    for (int num : bucket) {
                        arr[arrIdx++] = num;
                    }
                    bucket.clear();
                }
            }
            div *= 10;
        }
    }

    /**
     * Implement heap sort.
     *
     * It should be:
     * out-of-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * Use java.util.PriorityQueue as the heap. Note that in this
     * PriorityQueue implementation, elements are removed from smallest
     * element to largest element.
     *
     * Initialize the PriorityQueue using its build heap constructor (look at
     * the different constructors of java.util.PriorityQueue).
     *
     * Return an int array with a capacity equal to the size of the list. The
     * returned array should have the elements in the list in sorted order.
     *
     * @param data the data to sort
     * @return the array with length equal to the size of the input list that
     * holds the elements from the list in sorted order
     * @throws IllegalArgumentException if the data is null
     */
    public static int[] heapSort(List<Integer> data) {
        if (data == null) {
            throw new IllegalArgumentException("data cannot be null");
        }
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        int[] arr = new int[data.size()];
        for (int i = 0; i < data.size(); i++) {
            pq.add(data.get(i));
        }
        int index = 0;
        while (pq.size() > 0) {
            int element = pq.poll();
            arr[index] = element;
            index++;
        }
        return arr;
    }
}
