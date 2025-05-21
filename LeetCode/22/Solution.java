public class Solution {
    public List<String> generateParenthesis(int n) {
        List<String> ans = new ArrayList<>();
        backtrack(ans, new StringBuilder(), 0, 0, n);
        return ans;
    }
    
    private void backtrack(List<String> ans, StringBuilder sb, int open, int close, int max) {
        // 1) If we've built a string of length 2*max, it's complete
        if (sb.length() == 2 * max) {
            ans.add(sb.toString());
            return;
        }
        // 2) If we can still add '(', do so
        if (open < max) {
            sb.append('(');
            backtrack(ans, sb, open + 1, close, max);
            sb.deleteCharAt(sb.length() - 1);
        }
        // 3) If we can add ')' without invalidating, do so
        if (close < open) {
            sb.append(')');
            backtrack(ans, sb, open, close + 1, max);
            sb.deleteCharAt(sb.length() - 1);
        }
    }
} 