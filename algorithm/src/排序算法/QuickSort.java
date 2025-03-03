package 排序算法;

/**
 * 快速排序
 * 注意点：while循环中线从右往左找；一定要先将基准元素交换到最左边，然后while退出后再将基准元素交换到正确位置
 *平均时间复杂度为O(nlogn),最坏为O(n2)
 * @author: wanghaoran1
 * @create: 2025-02-26
 */
public class QuickSort {
    /**
     *
     * @param arr 要排序的数组
     * @param left 左索引
     * @param right 右索引
     */
    public static void quickSort(int[] arr, int left, int right) {
        if (left >= right) {
            return;
        }
        // 随机选择基准元素
        int pivotIndex = left + (int) (Math.random() * (right - left + 1));
        int pivot = arr[pivotIndex];
        // 将基准元素交换到最左边
        swap(arr, left, pivotIndex);
        int i = left;//左边界
        int j = right;//右边界
        while (i < j) {
            // 从右向左寻找第一个小于基准元素的元素
            while (i < j && arr[j] >= pivot) {//！！一定要先从右向左找
                j--;
            }
            // 从左向右寻找第一个大于基准元素的元素
            while (i < j && arr[i] <= pivot) {
                i++;
            }
            if (i < j) {//如果找到了，交换两边的值
                swap(arr, i, j);
            }
        }
        // 将基准元素放到正确的位置
        swap(arr, left, i);
        quickSort(arr, left, i - 1);
        quickSort(arr, i + 1, right);
    }

    private static void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    public static void main(String[] args) {
        int[] arr = {30, 40, 60, 10, 20, 50, 60};
        quickSort(arr, 0, arr.length - 1);
        for (int num : arr) {
            System.out.printf("%d ", num);
        }
    }
}
