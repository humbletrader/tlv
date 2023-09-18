# tlv
Technical challenge from TLV

# how to build/run/test ? 
although this is a scala application, I've made an effort to build it with maven so that you can easily build/run/test

# Solution description
A document is a set of ContourGroups on which we add new Contours/Rectangles (input). 
For each Contour added we check the groups that are close enough  
   - if close enough groups are found 
     - we create a new Contour out of the current Contour and the groups found as being "close enough" by computing a new Rectangle/Contour that encloses the original Contour and its "close enough" groups
     - we delete the "close enough" groups from the original document ( they have already been added to the new Contour - see above )
     - we re-do the scan for the newly created Contour (making sure the newly grown Contour does not have other "close enough" groups in the document)
   - if no groups are "close enough" we add the Contour to the list of Groups in the document.


# Assumptions 
The whole application is based on the assumption that the document ( id card, driver's licence, etc) has the x,y coordinates like: 

(0,0) ---------------> (100, 0) -------->  
   |
   |
   |
   |
   \/
(0, 100)
   |
   | 
   |
   \/

# Improvements
   * the most important improvement is to scan the groups only once for each new contour that is added to the document. ( probably an index with rectangle coordinates will decrease significantly the number of scans but multiple iteration may still be possible)
   * read the input from command line
   * read the config from command line
   * some implicit classes may be created to improve readability of the code 
     * Example : instead of ```isRectClose(rect: Contour, group: ContourGroup)``` we should add an implicit to the group so that we can write "group isRectClose rect"
     