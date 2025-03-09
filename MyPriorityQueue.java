
public class MyPriorityQueue {
    private Post[] postHeap;
    private int currentSize;
    private static final int InitialCapacity = 10000;

    public MyPriorityQueue(){
        this.postHeap = new Post[InitialCapacity];
        this.currentSize = 0;
    }
    public void insert(Post post){
        if (currentSize == postHeap.length - 1){ // no more place for new elements
            resize();
        }
        currentSize = currentSize + 1; // index of new element
        postHeap[currentSize] = post; //inserting new element
        percolateUp(currentSize); // finding the place of new element


    }
    private void resize(){
        Post[] newHeap = new Post[postHeap.length * 2 + 1];
        System.arraycopy(postHeap, 0, newHeap, 0, postHeap.length);
        postHeap = newHeap;

    }
    public Post deleteMax(){
        if (isEmpty()){
            return null;
        }
        Post maxPost = postHeap[1];
        postHeap[1] = postHeap[currentSize];
        currentSize = currentSize - 1;
        percolateDown(1);
        return maxPost;
    }
    public boolean isEmpty() {
        return currentSize == 0;
    }
    private void percolateUp(int index) {
        while (index > 1) { // Index should be greater than 1 to avoid checking 0th index because it is null.
            int parentIndex = index / 2; // reaches to parent's index

            if (postHeap[index].getLikes() > postHeap[parentIndex].getLikes()) { // checks the number of likes
                swap(index, parentIndex);
            } else if (postHeap[index].getLikes() == postHeap[parentIndex].getLikes() &&
                    postHeap[index].getPostId().compareTo(postHeap[parentIndex].getPostId()) > 0) { // checks the lexicographic order
                swap(index, parentIndex);
            } else { // it is right place
                break;
            }
            index = parentIndex;
        }
    }

    private void percolateDown(int index) {
        int leftChild = 2 * index;          // left child
        int rightChild = 2 * index + 1;     // right child
        int biggest = index;

        // checking the left child
        if (leftChild <= currentSize && postHeap[leftChild].getLikes() > postHeap[biggest].getLikes()) { // checks the number of likes
            biggest = leftChild;
        } else if (leftChild <= currentSize && postHeap[leftChild].getLikes() == postHeap[biggest].getLikes() &&
                postHeap[leftChild].getPostId().compareTo(postHeap[biggest].getPostId()) > 0) { // checks the lexicographic order
            biggest = leftChild;
        }

        // checking the right child
        if (rightChild <= currentSize && postHeap[rightChild].getLikes() > postHeap[biggest].getLikes()) { // checks the number of likes
            biggest = rightChild;
        } else if (rightChild <= currentSize && postHeap[rightChild].getLikes() == postHeap[biggest].getLikes() &&
                postHeap[rightChild].getPostId().compareTo(postHeap[biggest].getPostId()) > 0) { // checks the lexicographic order
            biggest = rightChild;
        }

        if (biggest != index) { // if there is bigger child, change them
            swap(index, biggest);
            percolateDown(biggest); // continue to percolate down
        }
    }

    /**
     * This method changes the place of ith and jth elements of postHeap
     * @param i is the index of child which percolates down
     * @param j is the index of child which take the place of postHeap[i]
     */
    private void swap(int i, int j) {
        Post temp = postHeap[i];
        postHeap[i] = postHeap[j];
        postHeap[j] = temp;
    }
}
