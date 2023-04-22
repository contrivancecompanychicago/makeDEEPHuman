import biojava.alignment.*;
import biojava.alignment.io.*;
import biojava.alignment.tree.*;
import java.lang.Object;
import java.lang.Math;

public class FastaReader implements Main{

    public static void main(String[] args) throws Exception {
        // Read the FASTA file
        AlignmentReader reader = new FastaReader("data.fasta");
        Alignment alignment = reader.read();

        // Analyze the data
        Tree tree = new UPGMATree(alignment);
        System.out.println(tree);
    }
}
