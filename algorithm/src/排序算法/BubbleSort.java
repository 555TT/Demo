package 排序算法;

/**
 * 冒泡排序
 *
 * @author: wanghaoran1
 * @create: 2025-02-26
 */
public class BubbleSort {
    public static void bubbleSort(int[] arr) {
        if (arr == null || arr.length == 0) {
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    //交换
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }

    public static void main(String[] args) {
        int[] arr = {32, 54, 12, 76, 0};
        bubbleSort(arr);
        for (int n : arr) {
            System.out.printf("%d ", n);
        }

    }
}
