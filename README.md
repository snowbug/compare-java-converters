Compare Java Converters
=======================

This repository has small test to compare performance of popular Java object conversion libraries.
Results of tests execution are available at http://blog.sokolenko.me/2013/05/dozer-vs-orika-vs-manual.html


Extended Tests
==============

The original Git repo is cloned and extended with the following enhancements:
  * Dozer: Upgrade from v5.4.0 (Dec. 2012) to v5.5.1 (Apr. 2014)
  * Orika: Upgrade from v1.4.1 (Feb. 2013) to v1.4.6 (May 2015)
  * Added new Mapping framework for comparison: Selma v0.14 (Mar. 2016)
  * Added "warm up" runs first before the actual benchmarking so frameworks that

Results
=======
*AVG Time Taken*
	dozer	    selma	orika	manual
	150,653	    1,466	11,730	2,200
	151,753	    1,466	11,363	2,566
	152,852	    1,467	12,096	2,566
*AVG*	151,753	    1,466	11,730	2,444


*MAX Time Taken*
	dozer	    selma	    orika	    manual
	25,434,601	852,231	    550,926	    312,667
	17,013,460	338,326	    428,865	    371,682
	31,742,576	226,529	    369,117	    271,614
*AVG*	24,730,212	472,362	    449,636	    318,654


Conclusion
==========
1st Choice: Selma
2nd Choice: Orika

Next Step
==========
Review the following capabilities of the two recommended libraries to make a final recommendation:
- Easiness of creating mapping
- Support for complex mapping with nested objects

