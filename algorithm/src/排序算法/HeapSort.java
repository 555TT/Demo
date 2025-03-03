package 排序算法;

/**
 * 堆排序
 * 大根堆：满足任意一个结点都大于等于它的全部左右子结点的完全二叉树
 * 建堆的时间复杂度：O(n),调整堆的时间复杂度:O(nlogn)
 * 不稳定
 */
public class HeapSort {
    public static void main(String[] args) {
        int[] arr = {16, 7, 3, 20, 17, 8, 17};

        heapSort(arr);

        for (int i : arr) {
            System.out.print(i + " ");
        }
    }


    /**
     * 创建堆
     *
     * @param arr 待排序列
     */
    private static void heapSort(int[] arr) {
        //创建堆
        for (int i = (arr.length - 1) / 2; i >= 0; i--) {
            //从第一个非叶子结点从下至上，从右至左调整结构
            adjustHeap(arr, i, arr.length);
        }

        //调整堆结构+交换堆顶元素与末尾元素
        for (int i = arr.length - 1; i > 0; i--) {//结束条件是>0，最后一个元素不需要动了
            //将堆顶元素与末尾元素进行交换
            int temp = arr[i];
            arr[i] = arr[0];
            arr[0] = temp;

            //重新对堆进行调整
            adjustHeap(arr, 0, i);
        }
    }

    /**
     * 调整堆
     *
     * @param arr    待排数组
     * @param root   要排序的子树的根节点
     * @param length 待排数组长度，或者说是最后一个元素的下标+1
     */
    private static void adjustHeap(int[] arr, int root, int length) {
        //将根结点元素另存起来
        int temp = arr[root];
        //左孩子索引
        int lChild = 2 * root + 1;

        while (lChild < length) {
            //右孩子索引
            int rChild = lChild + 1;
            // 如果有右孩子结点，并且右孩子结点的值大于左孩子结点，则选取右孩子结点
            if (rChild < length && arr[lChild] < arr[rChild]) {
                lChild++;
            }

            // 如果根结点的值已经大于孩子结点的值，则直接结束
            if (temp >= arr[lChild]) {
                break;
            }

            // 把孩子结点的值赋给根结点
            arr[root] = arr[lChild];

            //选取孩子结点的左孩子结点,继续向下筛选
            root = lChild;
            lChild = 2 * lChild + 1;
        }
        arr[root] = temp;
    }
}