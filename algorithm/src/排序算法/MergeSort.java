package 排序算法;

/**
 * 归并排序
 * 稳定。O(n*logn)
 */
public class MergeSort {
    /**
     * @param array 待排序数组
     * @param left  数组起始下标
     * @param right 数组末尾下标
     * @return
     */
    public static int[] mergeSort(int[] array, int left, int right) {
        int mid = (left + right) / 2;
        if (left < right) {
            // 先对左边进行归并排序
            mergeSort(array, left, mid);
            // 再对右边归并排序
            mergeSort(array, mid + 1, right);
            //对左右两边已经进行归并排序的有序数组进行合并
            mergeArray(array, left, mid, right);
        }
        return array;
    }

    /**
     * 合并两个有序数组
     *
     * @param array
     * @param left  左数组的起始索引
     * @param mid   mid是左数组的末尾索引，mid+1是右数组起始索引
     * @param right 右数组末尾索引
     */
    public static void mergeArray(int[] array, int left, int mid, int right) {
        int[] temp = new int[right - left + 1];//临时数组用于合并
        int i = left;//左半部分起始索引
        int j = mid + 1;//右半部分起始索引
        int k = 0;//临时数组的起始索引
        while (i <= mid && j <= right) {
            // 将两个有序数组合并到临时数组中
            if (array[i] < array[j]) {
                temp[k++] = array[i++];
            } else {
                temp[k++] = array[j++];
            }
        }
        // 左边剩余元素填充进temp中
        while (i <= mid) {
            temp[k++] = array[i++];
        }
        // 右边剩余元素填充进temp中
        while (j <= right) {
            temp[k++] = array[j++];
        }
        // 将临时数组中的元素复制回原数组
        k = 0;
        while (left <= right) {
            array[left++] = temp[k++];
        }
    }

    // 测试算法
    public static void main(String[] args) {
        int[] number = {4, 2, 6, 1, 9, 7, 8, 0, 3, 5};
        System.out.println("归并排序之前：");
        for (int i = 0; i < number.length; i++) {
            System.out.print(number[i] + " ");
        }
        mergeSort(number, 0, number.length - 1);
        System.out.println("\n归并排序之后：");
        for (int i = 0; i < number.length; i++) {
            System.out.print(number[i] + " ");
        }

    }
}