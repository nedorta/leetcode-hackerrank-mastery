# Product of Array Except Self - Solution Explanation

## 1. Problem Statement

You're given an integer array `nums` of length n.
Task: Return an array `answer` of the same length such that:
- `answer[i] = (product of all nums[j] for j != i)`

Additional requirements:
- No division allowed
- Must run in O(n) time
- O(1) extra space (the output array does not count)

## 2. Concrete Examples

| Example | Input | Output | Explanation |
|---------|-------|--------|-------------|
| 1 | [1,2,3,4] | [24,12,8,6] | For i=0: 2·3·4=24; i=1: 1·3·4=12; i=2: 1·2·4=8; i=3: 1·2·3=6 |
| 2 | [-1,1,0,-3,3] | [0,0,9,0,0] | Zeros zero out all but the one non-zero prefix/suffix product |

## 3. Two-Pass Prefix–Suffix Approach

### Prefix pass (left to right):
1. Maintain `prefix = 1`
2. At index i, set `output[i] = prefix`, then `prefix *= nums[i]`
3. After this, `output[i]` holds the product of all elements before i

### Suffix pass (right to left):
1. Maintain `suffix = 1`
2. At index i, multiply `output[i] *= suffix`, then `suffix *= nums[i]`
3. Now `output[i]` is (product of all before i) × (product of all after i)

This uses only two scalar accumulators (`prefix`, `suffix`) plus the output array.

## 4. Code Walkthrough

```java
int n = nums.length;
int[] output = new int[n];

// Prefix pass:
int prefix = 1;
for (int i = 0; i < n; i++) {
    output[i] = prefix;    // product of nums[0..i-1]
    prefix *= nums[i];     // include nums[i] for next positions
}

// Suffix pass:
int suffix = 1;
for (int i = n - 1; i >= 0; i--) {
    output[i] *= suffix;   // multiply by product of nums[i+1..n-1]
    suffix *= nums[i];     // include nums[i] for next (leftward) positions
}
```

### Code Section Breakdown

| Section | Purpose |
|---------|---------|
| `output[i] = prefix;` | Stores product of all elements to the left of index i |
| `prefix *= nums[i];` | Updates prefix to include nums[i] for the next index |
| `output[i] *= suffix;` | Multiplies by product of all elements to the right of index i |
| `suffix *= nums[i];` | Updates suffix to include nums[i] for the next (leftward) index |

## 5. Complexity Analysis

### Time: O(n)
- Two simple passes over the array
- Each operation within the loops is O(1)

### Space: O(1) extra
- Only two integer variables (`prefix`, `suffix`)
- Not counting the output array as per problem requirements

## 6. Key Takeaway

By cleverly combining a left‐to‐right prefix product pass with a right‐to‐left suffix product pass, you can compute each `answer[i]` without division and using only constant extra space. The key insight is that each element's contribution to the final product can be factored into prefix and suffix components. 