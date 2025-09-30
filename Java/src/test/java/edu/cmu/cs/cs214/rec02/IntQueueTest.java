package edu.cmu.cs.cs214.rec02;

import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.Assert.*;


/**
 * TODO: 
 * 1. The {@link LinkedIntQueue} has no bugs. We've provided you with some example test cases.
 * Write your own unit tests to test against IntQueue interface with specification testing method 
 * using mQueue = new LinkedIntQueue();
 * 
 * 2. 
 * Comment `mQueue = new LinkedIntQueue();` and uncomment `mQueue = new ArrayIntQueue();`
 * Use your test cases from part 1 to test ArrayIntQueue and find bugs in the {@link ArrayIntQueue} class
 * Write more unit tests to test the implementation of ArrayIntQueue, with structural testing method
 * Aim to achieve 100% line coverage for ArrayIntQueue
 *
 * @author Alex Lockwood, George Guo, Terry Li
 */
public class IntQueueTest {

    private IntQueue mQueue;
    private List<Integer> testList;

    /**
     * Called before each test.
     */
    @Before
    public void setUp() {
        // comment/uncomment these lines to test each class
        // mQueue = new LinkedIntQueue();
       mQueue = new ArrayIntQueue();

        testList = new ArrayList<>(List.of(1, 2, 3));
    }

    @Test
    public void testIsEmpty() {
        // This is an example unit test
        assertTrue(mQueue.isEmpty());
    }

    @Test
    public void testNotEmpty() {
        // Test that queue is not empty after adding elements
        mQueue.enqueue(42);
        assertFalse(mQueue.isEmpty());
        assertEquals(1, mQueue.size());
    }

    @Test
    public void testPeekEmptyQueue() {
        // Test that peek returns null when queue is empty
        assertNull(mQueue.peek());
    }

    @Test
    public void testPeekNoEmptyQueue() {
        // Test that peek returns the head element without removing it
        mQueue.enqueue(10);
        mQueue.enqueue(20);
        mQueue.enqueue(30);
        
        assertEquals(Integer.valueOf(10), mQueue.peek());
        assertEquals(3, mQueue.size()); // Size should remain unchanged
        assertEquals(Integer.valueOf(10), mQueue.peek()); // Should still be the same
    }

    @Test
    public void testEnqueue() {
        // This is an example unit test
        for (int i = 0; i < testList.size(); i++) {
            mQueue.enqueue(testList.get(i));
            assertEquals(testList.get(0), mQueue.peek());
            assertEquals(i + 1, mQueue.size());
        }
    }

    @Test
    public void testDequeue() {
        // Test dequeue functionality - FIFO behavior
        mQueue.enqueue(1);
        mQueue.enqueue(2);
        mQueue.enqueue(3);
        
        // Test dequeue from empty queue
        IntQueue emptyQueue = new LinkedIntQueue();
        assertNull(emptyQueue.dequeue());
        
        // Test FIFO order
        assertEquals(Integer.valueOf(1), mQueue.dequeue());
        assertEquals(2, mQueue.size());
        assertEquals(Integer.valueOf(2), mQueue.peek());
        
        assertEquals(Integer.valueOf(2), mQueue.dequeue());
        assertEquals(1, mQueue.size());
        assertEquals(Integer.valueOf(3), mQueue.peek());
        
        assertEquals(Integer.valueOf(3), mQueue.dequeue());
        assertEquals(0, mQueue.size());
        assertTrue(mQueue.isEmpty());
        assertNull(mQueue.peek());
    }

    @Test
    public void testContent() throws IOException {
        // This is an example unit test
        InputStream in = new FileInputStream("src/test/resources/data.txt");
        try (Scanner scanner = new Scanner(in)) {
            scanner.useDelimiter("\\s*fish\\s*");

            List<Integer> correctResult = new ArrayList<>();
            while (scanner.hasNextInt()) {
                int input = scanner.nextInt();
                correctResult.add(input);
                System.out.println("enqueue: " + input);
                mQueue.enqueue(input);
            }

            for (Integer result : correctResult) {
                assertEquals(mQueue.dequeue(), result);
            }
        }
    }

    @Test
    public void testClear() {
        // Test clear functionality
        mQueue.enqueue(1);
        mQueue.enqueue(2);
        mQueue.enqueue(3);
        assertEquals(3, mQueue.size());
        assertFalse(mQueue.isEmpty());
        
        mQueue.clear();
        assertEquals(0, mQueue.size());
        assertTrue(mQueue.isEmpty());
        assertNull(mQueue.peek());
        assertNull(mQueue.dequeue());
    }

    @Test
    public void testEnqueueReturnValue() {
        // Test that enqueue returns true for successful operations
        assertTrue(mQueue.enqueue(1));
        assertTrue(mQueue.enqueue(2));
        assertTrue(mQueue.enqueue(3));
    }

    @Test
    public void testFIFOOrder() {
        // Test that queue maintains FIFO order
        List<Integer> input = List.of(5, 3, 8, 1, 9);
        List<Integer> expected = new ArrayList<>(input);
        
        // Enqueue all elements
        for (Integer value : input) {
            mQueue.enqueue(value);
        }
        
        // Dequeue and verify FIFO order
        List<Integer> actual = new ArrayList<>();
        while (!mQueue.isEmpty()) {
            actual.add(mQueue.dequeue());
        }
        
        assertEquals(expected, actual);
    }

    @Test
    public void testSizeAfterOperations() {
        // Test size changes after various operations
        assertEquals(0, mQueue.size());
        
        mQueue.enqueue(1);
        assertEquals(1, mQueue.size());
        
        mQueue.enqueue(2);
        assertEquals(2, mQueue.size());
        
        mQueue.peek(); // Should not change size
        assertEquals(2, mQueue.size());
        
        mQueue.dequeue();
        assertEquals(1, mQueue.size());
        
        mQueue.dequeue();
        assertEquals(0, mQueue.size());
        
        mQueue.clear(); // Should remain 0
        assertEquals(0, mQueue.size());
    }

    @Test
    public void testMultipleEnqueueDequeue() {
        // Test multiple cycles of enqueue/dequeue
        for (int cycle = 0; cycle < 3; cycle++) {
            // Enqueue some values
            for (int i = 1; i <= 3; i++) {
                mQueue.enqueue(i + cycle * 3);
            }
            
            // Dequeue all values
            for (int i = 1; i <= 3; i++) {
                assertEquals(Integer.valueOf(i + cycle * 3), mQueue.dequeue());
            }
            
            // Queue should be empty after each cycle
            assertTrue(mQueue.isEmpty());
        }
    }


}
