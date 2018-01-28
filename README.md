GEDCOM Parser Challenge

GEDCOM is the "GEnealogical Data COMmunication" file format. It is a plain-text electronic format used to transfer genealogical data.
The purpose of this challenge is to develop a simple parser that can convert a GEDCOM file to XML.
GEDCOM Format
The GEDCOM file format is very straightforward. Each line represents a node in a tree. It looks something like this:
  0 @I1@ INDI
  1 NAME Jamis Gordon /Buck/
  2 SURN Buck
  2 GIVN Jamis Gordon
  1SEXM
  ...
In general, each line is formatted thus:
  LEVEL TAG-OR-ID [DATA]
The LEVEL is an integer, representing the current depth in the tree. If subsequent lines have greater levels than the current node, they are children of the current node.
TAG-OR-ID is either a tag that identifies the type of data in that node, or it is a unique identifier. Tags are 3- or 4-letter words in uppercase. The unique identifiers are always text surrounded by "@" characters (i.e., "@I54@"). If an ID is given, the DATA is the type of the subtree that is identified.
Variable whitespace is allowed between the level and the tag. Blank lines are ignored.
 The Challenge
The challenge, then, is to create a parser that takes a GEDCOM file as input and converts it to XML. 
