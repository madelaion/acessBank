package br.com.valepresente.stream;

/**
 * Contrato define as funcionalidades para operar sob uma string.
 * 
 * @see NonRepeatedCharStream
 */
public interface Stream {

	/**
	 * @return o próximo caracter disponível no Stream. 
	 * <strong>Note:</strong> Deve ser utilizado em conjunto ao método <code>hasNext</code>.
	 * @throws RuntimeException caso não exista um próximo caracter disponível.
	 */
	char getNext();
	
	/**
	 * @return boolean indicando se existe um próximo caracter disponível no Stream.
	 */
	boolean hasNext();

}
