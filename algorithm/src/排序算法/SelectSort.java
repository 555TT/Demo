package 排序算法;


/**
 * 选择排序：简单选择排序和堆排序
 */
public class SelectSort {
    public static void main(String[] args) {
        /**
         * 从下标0开始存储完全二叉树，下标为i的结点，几个性质：
         * 左子结点下标：2*i+1，右子结点：2*i+2
         * 父结点：(i-1)/2
         * 最后一个非叶子结点：(length-1)/2
         */
        int[] arr = {5, 1, 1, 2, 0, 0};

        selectSort(arr);

        for (int i : arr) {
            System.out.print(i + " ");
        }
    }

    /**
     * 简单选择排序
     * 分为两个序列：已完成排序，和未完成排序
     * 起始两个序列：[0][1,n-1]
     * 从未排序序列中选择一个最小值与已完成排序序列最后一个元素交换，不断重复
     * 不稳定
     *
     * @param nums
     */
    private static void selectSort(int[] nums) {
        int n = nums.length;
        int min;
        for (int i = 0; i < n; i++) {
            min = i;
            for (int j = i + 1; j < n; j++) {
                if (nums[j] < nums[min]) {
                    min = j;
                }
            }
            if (i != min) {
                swap(nums, i, min);
            }
        }
    }

        private static void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }


    /**
     * 堆排序
     * 大根堆：满足任意一个结点都大于等于它的全部左右子结点的完全二叉树
     * 建堆的时间复杂度：O(n),调整堆的时间复杂度:O(nlogn)
     * 不稳定
     */
    private static void heapSort(int[] arr) {
        //创建堆
        for (int i = (arr.length) / 2 - 1; i >= 0; i--) {
            //从第一个非叶子结点从下至上，从右至左调整结构
            adjustHeap(arr, i, arr.length);
        }

        //调整堆结构+交换堆顶元素与当前末尾元素，arr[0]是堆顶元素，arr[i]是当前末尾元素
        for (int i = arr.length - 1; i > 0; i--) {//结束条件是>0，最后剩一个元素不需要动了
            //将堆顶元素与末尾元素进行交换
            int temp = arr[i];
            arr[i] = arr[0];
            arr[0] = temp;

            //重新对堆进行调整
            adjustHeap(arr, 0, i);
        }
    }

    /**
     * 将堆调整为大根堆
     *
     * @param arr    待排数组
     * @param root   要排序的子树的根节点
     * @param length 待排数组长度，或者说是最后一个元素的下标+1
     */
    private static void adjustHeap(int[] arr, int root, int length) {
        int rootValue = arr[root];
        int lChild = 2 * root + 1, rChild = 2 * root + 2;
        while (lChild < length) {
            //maxChild是左右孩子较大的一个
            int maxChild = lChild;
            if (rChild < length && arr[rChild] > arr[lChild]) {
                maxChild = rChild;
            }
            //根节点已经比左右子孩子大了
            if (arr[root] >= arr[maxChild]) {
                break;
            }
            arr[root] = arr[maxChild];
            root = maxChild;
            lChild = 2 * root + 1;
            rChild = 2 * root + 2;
        }
        arr[root] = rootValue;
    }
}