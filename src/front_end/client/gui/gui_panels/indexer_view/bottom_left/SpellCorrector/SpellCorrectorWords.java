package front_end.client.gui.gui_panels.indexer_view.bottom_left.SpellCorrector;


import java.io.*;
import java.util.Scanner;


public class SpellCorrectorWords
implements SpellCorrector
{
	TrieWords word = new TrieWords();
	public SpellCorrectorWords()
	{
	}

	@Override
	public void useDictionary(String dictionary) throws IOException
	{
       // PrintWriter p_of_b_of_f = null;
        try{
        //	FileWriter f = null;
        //	BufferedWriter b_of_f = new BufferedWriter(f);
        //	p_of_b_of_f = new PrintWriter(b_of_f);
        	
            FileInputStream fis = null;
            try
            {
                fis = new FileInputStream(dictionary);
            }
            catch(FileNotFoundException e)
            {
                System.err.println("Better NOT get here, again");
            }
            BufferedInputStream b_of_fis = new BufferedInputStream(fis);
            Scanner scanner = new Scanner(b_of_fis);

            while(scanner.hasNext())
        	{
            	String output = scanner.next();
            	output.toLowerCase();
            	word.add(output);
        	}
            scanner.close();
             
        }
        finally
        { 
        };		
		
	}

	@Override
	public String suggestSimilarWord(String inputWord)
			throws NoSimilarWordFoundException {
		String output = null;
		if(word.find(inputWord) != null)
		{	
			System.out.println(inputWord);
			return inputWord;
		}
		else if(word.find(inputWord) == null)
        {
        	output = word.similar(inputWord);
        	System.out.println("We got to this point.");
        	System.out.println(output);
        	if(output == null)
        	{
            	throw new NoSimilarWordFoundException();
        	}
        	else
        	{
            	return output;	
        	}
        }
        else
        {
        	throw new NoSimilarWordFoundException();
        }
	}

}
