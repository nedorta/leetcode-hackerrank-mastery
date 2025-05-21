# Range Sum Query - Immutable - Solution Explanation

## 1. Problem Statement

Design a class `NumArray` to efficiently answer repeated queries of the form:

- `sumRange(left, right)`: Return the sum of the subarray
  `nums[left] + nums[left+1] + … + nums[right]`
  for a fixed integer array nums.

You will be given the array once at construction, then up to 10⁴ calls to sumRange.

## 2. Concrete Examples

| Example | nums | Queries | Output | Explanation |
|---------|------|---------|--------|-------------|
| 1 | [-2,0,3,-5,2,-1] | (0,2), (2,5), (0,5) | 1, -1, -3 | As in the prompt: sums of subarrays [−2…3], etc. |

## 3. Brute-Force Thought (O(n) per query)

Each call to sumRange(left, right) could loop from left to right and add up elements:

```java
int sum = 0;
for (int i = left; i <= right; i++) {
    sum += nums[i];
}
return sum;
```

- Time per query: O(n) in the worst case
- Total time: up to O(q·n), where q ≤ 10⁴ and n ≤ 10⁴ ⇒ can be 10⁸ operations, borderline slow

## 4. Optimal O(n + q) Approach: Prefix Sum (aka Cumulative Sum)

### 4.1. Key Idea

Preprocess once in O(n) to build an auxiliary array `prefix` where:

```
prefix[0] = 0
prefix[i] = nums[0] + nums[1] + … + nums[i-1]
```

Then any subarray sum `nums[left] + … + nums[right]` is:
- `prefix[right+1] − prefix[left]`
- in O(1) time

## 5. Walkthrough of the Code

```java
class NumArray {
    // prefix[i] will hold the sum of nums[0..i-1]
    private int[] prefix;

    // Constructor: O(n) preprocessing
    public NumArray(int[] nums) {
        int n = nums.length;
        // allocate one extra slot so prefix[0]=0 and prefix[i+1]=sum up to nums[i]
        prefix = new int[n + 1];
        prefix[0] = 0;
        // build prefix sums
        for (int i = 0; i < n; i++) {
            // sum up to index i is sum up to i-1 plus nums[i]
            prefix[i + 1] = prefix[i] + nums[i];
        }
    }
    
    // sumRange: O(1) per query
    public int sumRange(int left, int right) {
        // sum nums[left..right] = prefix[right+1] - prefix[left]
        return prefix[right + 1] - prefix[left];
    }
}
```

### Code Breakdown

| Code Line(s) | Explanation |
|--------------|-------------|
| `private int[] prefix;` | Stores cumulative sums. |
| `prefix = new int[n+1]; prefix[0] = 0;` | Initialize with an extra zero at front. |
| `prefix[i+1] = prefix[i] + nums[i];` | Build running total so prefix[k] = sum of the first k elements. |
| `sumRange(...)` | Uses the identity: sum(nums[left..right]) = prefix[right+1] − prefix[left]. |

## 6. Complexity Analysis

### Initialization:
- Time: O(n) to build the prefix array
- Space: O(n) extra for prefix

### Each Query:
- Time: O(1) — two array accesses and one subtraction
- Space: O(1) auxiliary

Thus overall O(n + q) time for n elements and q queries, which easily handles up to 10⁴ of each.

## 7. Key Takeaway

When you need to answer many range-sum queries on a static array, the prefix-sum (cumulative-sum) pattern reduces each query to constant time after a linear preprocessing step. 