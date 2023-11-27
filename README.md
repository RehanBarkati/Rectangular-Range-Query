# Rectangular-Range-Query

Problem statement : Given the locations of restaurant as latitude and longitude (x,y) and users location. Find the number of resturants which are within (<= 100) latitude and 100 longitude units of users location.

Algorithm : I implemented the 2-d trees to process the queries efficiently. 

Step 1:
To build a 2-d tree from a given collection of points follow this algorithm: At the root
node, split the points based on the value of first dimension of the points. All the points
whose value at the first dimension is less than or equal to the value of the median go to
the left sub-tree and the rest go to the right sub-tree. For any node at depth d, if d is
even, split the points based on the first dimension. Otherwise, split the points based on
the second dimension. Continue this process until you reach the leaf nodes - nodes with
one point. Also, at each node during this process, store the number of points under the
node and the orthogonal ’range’ covered by the node. The range of a node is based on the
splits from root to that node and is of the form ((xmin, xmax],(ymin, ymax])), except when
xmax or ymax is inf. ’]’ bracket indicates closed range with end point included whereas ’)’
bracket indicates open range. The range of the root is ((−inf, +inf),(−inf, +inf)) and
with each split, the range of the children can be found from that of the parent.

Step2: Counting the number of points within the rectangular range R.
At the root node, find the intersection of R with the range of the children. Finding this
intersection should be O(1) using the range stored at each node. Consider a child c - if
range(c) is fully contained in R, then add the count of points in c to the total count of
points within range R; if range(c) is fully outside R then skip node c; else if range(c)
intersects R then recursively call the algorithm on c with c as the root. Repeat the same
with the other child. On reaching a leaf node, simply see if the node is contained in R
and update the count of points in range accordingly.
