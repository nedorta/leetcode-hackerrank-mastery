class Solution {
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        boolean[] used = new boolean[nums.length];
        backtrack(nums, used, new ArrayList<>(), result);
        return result;
    }

    private void backtrack(int[] nums, boolean[] used,
                           List<Integer> current, List<List<Integer>> result) {
        // 1) If current permutation is complete, add a copy to result
        if (current.size() == nums.length) {
            result.add(new ArrayList<>(current));
            return;
        }
        // 2) Try each unused number in turn
        for (int i = 0; i < nums.length; i++) {
            if (used[i]) continue;            // skip already used
            used[i] = true;                  
            current.add(nums[i]);             
            backtrack(nums, used, current, result);
            // 3) Backtrack: undo choice
            current.remove(current.size() - 1);
            used[i] = false;
        }
    }
} 