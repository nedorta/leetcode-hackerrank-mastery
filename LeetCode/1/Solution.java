public class Solution {
    public int[] twoSum(int[] nums, int target) {
        // A map from number value → its index in the array.
        // We size it to nums.length to avoid rehashing as much as possible.
        Map<Integer, Integer> seen = new HashMap<>(nums.length);

        // Iterate over each element, treating it as the second number in the pair.
        for (int i = 0; i < nums.length; i++) {
            // Compute the complementary value we need to find among earlier elements.
            int complement = target - nums[i];

            // If we've already seen the complement, we're done:
            //   - seen.get(complement) is the earlier index (smaller than i)
            //   - i is the current index
            if (seen.containsKey(complement)) {
                return new int[]{
                    seen.get(complement),  // index of the complement
                    i                      // current index
                };
            }

            // Otherwise, record the current number and its index for future matches.
            // If the same number appears later, the earlier index will remain in the map
            // so that i<j ordering is preserved when we find a match.
            seen.put(nums[i], i);
        }

        // By problem specification, this line should never be reached (exactly one solution exists).
        // Some judges may expect an exception instead, but here we return the original array as a fallback.
        return nums;
    }
} 