package org.sketch.example;

import org.junit.Assert;
import org.junit.Test;

public class SketchTest {

	public class AnExtPrinter implements ExtPrinter {

		private String printedText;

		public String getPrintedText() {
			// TODO Auto-generated method stub
			return printedText;
		}

		@Override
		public void print(String text) {
			printedText = text;
		}

	}

	public class Printer {

		private ExtPrinter anExtPrinter;

		public Printer(ExtPrinter anExtPrinter) {
			this.anExtPrinter = anExtPrinter;
			// TODO Auto-generated constructor stub
		}

		public void print(String text) {
			anExtPrinter.print(text);
		}

	}

	@Test
	public void test() {
		AnExtPrinter anExtPrinter = new AnExtPrinter();
		Printer myPrinter = new Printer(anExtPrinter);
		myPrinter.print("a text");
		Assert.assertEquals("a text", anExtPrinter.getPrintedText());
	}

}
