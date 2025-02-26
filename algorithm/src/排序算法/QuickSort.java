package 排序算法;

/**
 * 快速排序
 *
 * @author: wanghaoran1
 * @create: 2025-02-26
 */
public class QuickSort {
    public static void quickSort(int[] arr, int left, int right) {
        if (left >= right) {
            return;
        }
        int i = left;//左边界
        int j = right;//右边界
        int x = arr[i];//基准元素，这里选择左边界元素作为基准元素，用x暂存基准元素
        while (i < j) {
            // 从右向左寻找第一个小于基准元素的元素
            while (i < j && arr[j] >= x) {//！！一定要先从右向左找
                j--;
            }
            // 从左向右寻找第一个大于基准元素的元素
            while (i < j && arr[i] <= x) {
                i++;
            }
            if (i < j) {//如果找到了，交换两边的值
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        // 将基准元素放到正确的位置
        arr[left] = arr[i];//arr[i]也是小于基准元素的值，将它放到最左边
        arr[i] = x;//将基准元素放到i这个中间位置
        quickSort(arr, left, i - 1);
        quickSort(arr, i + 1, right);
    }

    public static void main(String[] args) {
        int[] arr = {30, 40, 60, 10, 20, 50};
        quickSort(arr, 0, arr.length - 1);
        for (int num : arr) {
            System.out.printf("%d ", num);
        }
    }
}
