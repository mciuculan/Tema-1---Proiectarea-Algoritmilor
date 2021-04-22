build:
	javac Crypto.java Stocks.java Valley.java Ridge.java
run-p1:
	java Crypto
run-p2:
	java Stocks
run-p3:
	java Valley
run-p4:
	java Ridge
clean:
	rm -r Crypto Stocks Valley Ridge
