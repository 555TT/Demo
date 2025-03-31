package 排序算法;

/**
 * 插入排序：简单插入排序和希尔排序
 *
 * @author: wanghaoran1
 * @create: 2025-03-31
 */
public class InsertSort {

    /**
     * 简单插入排序
     *稳定
     * @param nums
     */
    private static void insertionSort(int[] nums) {
        int preIndex, current;
        int n = nums.length;
        for (int i = 1; i < n; i++) {
            preIndex = i - 1;
            current = nums[i];
            //找到合适的插入位置
            while (preIndex >= 0 && nums[preIndex] > current) {
                nums[preIndex + 1] = nums[preIndex];
                preIndex--;
            }
            nums[preIndex + 1] = current;
        }
    }

    /**
     * 希尔排序
     *不稳定
     * @param nums 待排序的数组
     */
    private static void shellSort(int[] nums) {
        int n = nums.length;
        //每次插入排序跳的步数，最后步数为1也就是对整个数组插入排序
        for (int gap = n / 2; gap >= 1; gap /= 2) {
            //i初始为gap也就是从该组序列的第二个元素开始
            for (int i = gap; i < n; i++) {
                int temp = nums[i];
                int j;
                for (j = i; j >= gap && nums[j - gap] > temp; j -= gap) {
                    nums[j] = nums[j - gap];
                }
                nums[j] = temp;
            }
        }
    }

    public static void main(String[] args) {
        int[] nums = {12, 34, 54, 2, 3, 8, 19, 23, 5, 15};
        shellSort(nums);
        for (int i : nums) {
            System.out.print(i + " ");
        }
    }

}
