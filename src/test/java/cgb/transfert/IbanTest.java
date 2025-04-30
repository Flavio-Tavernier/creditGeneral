package cgb.transfert;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import cgb.classesMetier.iban.ToolsIban;
public class IbanTest {

	
	
	
	
	
	
	/**
     * Methode de test de recuperation du code pays dans un IBAN
     * return void
     * @throws Exception 
     */
    @Test
    public void testIbanGetCountryCode() throws Exception {
    	ToolsIban outilsIban = ToolsIban.getInstance();
    	
    	String iban = "FR44123412341234123400";
    	assertEquals("FR", outilsIban.getCountryCode(iban), "le code pays doit etre 'FR'");
    }
    
    /**
     * Methode de test de recuperation du code de controle de l IBAN
     * return void
     * @throws Exception 
     */
    @Test
    public void testIbanGetControlNumber() throws Exception {
    	ToolsIban outilsIban = ToolsIban.getInstance();
    	
    	String iban = "FR44123412341234123400";
    	assertEquals("44", outilsIban.getControlNumber(iban), "le code de controle doit etre '44'");
    }
    
    /**
     * Methode de test de recuperation du BBAN de l IBAN
     * return void
     * @throws Exception 
     */
    @Test
    public void testIbanGetBBAN() throws Exception {
    	ToolsIban outilsIban = ToolsIban.getInstance();
    	
    	String iban = "FR44123412341234123400";
    	assertEquals("123412341234123400", outilsIban.getBBAN(iban), "le BBAN doit etre '123412341234123400'");
    }
    
    /**
     * Methode de test de structure de l IBAN
     * return void
     * @throws Exception
     */
    @Test
    public void testIbanStructureValide() throws Exception {
    	ToolsIban outilsIban = ToolsIban.getInstance();
    	
    	String iban = "FR44123412341234123400";
    	assertEquals(true, outilsIban.isIbanStructureValide(iban), "Structure IBAN invalide");
    }
    
    /**
     * Methode de test de structure de l IBAN
     * return void
     * @throws Exception
     */
    @Test
    public void testIbanStructureInvalide() throws Exception {
    	ToolsIban outilsIban = ToolsIban.getInstance();
    	
    	String iban = "FRR44123412341234123400";
    	assertEquals(false, outilsIban.isIbanStructureValide(iban), "Structure IBAN valide");
    }
    
    /**
     * Methode de test de la validite de l IBAN
     * return void
     * @throws Exception
     */
    @Test
    public void testIbanValide() throws Exception {
    	ToolsIban outilsIban = ToolsIban.getInstance();
    	
    	String iban = "FR44123412341234123400";
    	assertEquals(true, outilsIban.isIbanStructureValide(iban), "IBAN invalide");
    }
}














