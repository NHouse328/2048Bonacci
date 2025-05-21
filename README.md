<div class="MarkdownOutput MarkdownOutput_dark cm-s-monokai"><h1 id="markdown-2048-bonacci">2048-bonacci</h1>
<p>2048 is a tiny video game that was super hype some years ago. You had to fuse two equal numbers to create its double. So 1 and 1 became 2, 2 and 2 became 4, and so on.</p>
<p>Fibonacci was a famous mathematician who made the super-hype Fibonacci sequence you may have heard about: <code>U(n+1) = U(n) + U(n-1)</code>. The first number of the sequence are 1, 1, 2, 3, 5, 8, 13, …</p>
<p>Let’s combine these two hype things to create a super-super-hype game!</p>
<p>2048-bonacci plays on a 4x4 square. Each square is either empty or contains a number of the Fibonacci sequence.</p>
<p>You are given an initial board situation (a 2D array of integers) and a pushing direction (up, left, down, or right). Then, you must compute the board contents after the push and return an updated 2D array of integers.</p>
<p>The value of integers in the array is guaranteed to be less than 2^16 and are all Fibonacci numbers. The value 0 means the square is empty.</p>
<h4 id="markdown-rule-1">Rule 1</h4>
<p>Numbers move as far as possible in the pushing direction.</p>
<pre><code>-------------                       -------------
| 2|  |  |  |                       |  |  |  |  |
-------------                       -------------
|  |  |13|  |                       |  |  |  |  |
-------------  =&gt; push downward =&gt;  -------------
|  |  |  |  |                       | 2|  |  |  |
-------------                       -------------
| 5|  |  |  |                       | 5|  |13|  |
-------------                       -------------
</code></pre>
<h4 id="markdown-rule-2">Rule 2</h4>
<p>When two consecutive numbers in the Fibonacci sequence are pushed one on another, they fuse into the next number.</p>
<pre><code>-------------                       -------------
|  |  | 1| 2|                       |  |  |  | 3|
-------------                       -------------
| 1|  | 1|  |                       |  |  |  | 2|
-------------  =&gt; push rightward =&gt; -------------
|  | 8| 5|  |                       |  |  |  |13|
-------------                       -------------
|  | 5| 8|  |                       |  |  |  |13|
-------------                       -------------
</code></pre>
<h4 id="markdown-rule-3">Rule 3</h4>
<p>Fusing orders are resolved in the backward direction of the push.</p>
<p>A fused number can not be fused once again in the same turn.</p>
<pre><code>-------------                 -------------                 -------------
|  | 1| 2| 3|                 |  |  | 1| 5|                 |  |  | 1| 5|
-------------                 -------------                 -------------
|  | 3| 2| 1|                 |  |  | 3| 3|                 |  |  | 3| 3|
------------- =&gt; rightward =&gt; ------------- =&gt; rightward =&gt; -------------
|  |  |  |  |                 |  |  |  |  |                 |  |  |  |  |
-------------                 -------------                 -------------
|  | 5| 3| 5|                 |  |  | 5| 8|                 |  |  |  |13|
-------------                 -------------                 -------------
</code></pre>
<h4 id="markdown-rule-4">Rule 4</h4>
<p>Numbers can move to a square that a fusing has just emptied.</p>
<pre><code>-------------                     -------------
| 1|  |  |  |                     | 2|  |  |  |
-------------                     -------------
| 1|  |  |  |                     | 2|  |  |  |
-------------  =&gt; push upward =&gt;  -------------
| 1|  |  |  |                     |  |  |  |  |
-------------                     -------------
| 1|  |  |  |                     |  |  |  |  |
-------------                     -------------
</code></pre>
<p>Let’s hype!</p>
</div>