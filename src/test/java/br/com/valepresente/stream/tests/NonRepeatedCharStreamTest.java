package br.com.valepresente.stream.tests;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Test;

import br.com.valepresente.stream.NonRepeatedCharStream;
import br.com.valepresente.stream.Stream;

/**
 * Test Case do componente <code>NonRepeatedCharStream</code>.
 * 
 */
public class NonRepeatedCharStreamTest {

	@Test(expected=IllegalArgumentException.class)
	public void testCriarStreamSemConteudo() {
		Stream st = new NonRepeatedCharStream("");
		st.hasNext();
	}
	
	@Test
	public void testHasNextStreamVazio() {
		Stream st = new NonRepeatedCharStream("AAA");
		assertFalse(st.hasNext());
		
		st = new NonRepeatedCharStream("ABCABC");
		assertFalse(st.hasNext());
	}
	
	@Test(expected=NoSuchElementException.class)
	public void testGetNextStreamVazio() {
		Stream st = new NonRepeatedCharStream("WZZW");
		st.getNext();
	}
	
	@Test
	public void testOnlyFirstCharStream() {
		Stream st = new NonRepeatedCharStream("Java");
		assertTrue(st.hasNext());
		assertSame('J', st.getNext());
		
		st = new NonRepeatedCharStream("programar por diversão");
		assertTrue(st.hasNext());
		assertSame('g', st.getNext());
		
		st = new NonRepeatedCharStream("pneumoultramicroscopicossilicovulcaniotico");
		assertTrue(st.hasNext());
		char c = st.getNext();
		assertNotEquals('p', c);
		assertSame('e', c);
	}
	
	@Test(expected=NoSuchElementException.class)
	public void testOnlyFirstInvalido() {
		Stream st = new NonRepeatedCharStream("ssempprrre");
		assertTrue(st.hasNext());
		assertSame('m', st.getNext());
		assertNotEquals('e', st.getNext());
	}
	
	@Test
	public void testAllCharsStream() {
		Stream st = new NonRepeatedCharStream("escrevendo um conteudo maior para testar o stream");
		List<Character> actuals = new ArrayList<>();
		while (st.hasNext()) {
			actuals.add(st.getNext());
		}
		Character[] expecteds = new Character[] {'v', 'i', 'p'};
		assertArrayEquals(expecteds, actuals.toArray(new Character[]{}));
		
		st = new NonRepeatedCharStream("testando novamente sem perguntar se tem caracter disponível");
		assertSame('g', st.getNext());
		assertSame('u', st.getNext());
		assertSame('i', st.getNext());
		assertTrue("í".equals(""+st.getNext()));
		assertSame('l', st.getNext());
		assertFalse(st.hasNext());
	}
	
	@Test
	public void testSpecialCharsStream() {
		Stream st = new NonRepeatedCharStream("aAbBABac");
		List<Character> actuals = new ArrayList<>();
		while (st.hasNext()) {
			actuals.add(st.getNext());
		}
		Character[] expecteds = new Character[] {'b', 'c'};
		assertArrayEquals(expecteds, actuals.toArray(new Character[]{}));
		
		st = new NonRepeatedCharStream("123Aa -25,0;Eó");
		actuals = new ArrayList<>();
		while (st.hasNext()) {
			actuals.add(st.getNext());
		}
		expecteds = new Character[] {'1', '3', 'A', 'a', ' ', '-', '5', ',', '0', ';', 'E', 'ó'};
		assertArrayEquals(expecteds, actuals.toArray(new Character[]{}));
	}

}
