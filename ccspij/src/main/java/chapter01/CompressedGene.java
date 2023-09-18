package chapter01;

import java.util.BitSet;

import org.apache.commons.lang3.RandomStringUtils;

public class CompressedGene {
    private BitSet bitSet;
    private int length;

    public CompressedGene(String gene) {
        compress(gene);
    }

    private void compress(String gene) {
        length = gene.length();

        bitSet = new BitSet(length * 2);

        final String upperGene = gene.toUpperCase();

        for (int i = 0; i < length; i++) {
            final int firstLocation = 2 * i;
            final int secondLocation = 2 * i + 1;
            switch (upperGene.charAt(i)) {
                case 'A':
                    bitSet.set(firstLocation, false);
                    bitSet.set(secondLocation, false);
                    break;
                case 'C':
                    bitSet.set(firstLocation, false);
                    bitSet.set(secondLocation, true);
                    break;
                case 'G':
                    bitSet.set(firstLocation, true);
                    bitSet.set(secondLocation, false);
                    break;
                case 'T':
                    bitSet.set(firstLocation, true);
                    bitSet.set(secondLocation, true);
                    break;

                default:
                    throw new IllegalArgumentException("some chars are oher than ACGT");
            }
        }
    }

    public String decompress() {
        if (bitSet == null) {
            return "";
        }

        StringBuilder builder = new StringBuilder(length);
        for (int i = 0; i < (length * 2); i += 2) {
            final int firstBit = (bitSet.get(i) ? 1 : 0);
            final int secondBit = (bitSet.get(i + 1) ? 1 : 0);
            // refer to:
            // https://docs.oracle.com/javase/tutorial/java/nutsandbolts/operators.html
            // claim: shift operators (like <<) have higher precedence than bitwise
            // inclusive OR |
            final int lastBits = firstBit << 1 | secondBit;
            switch (lastBits) {
                case 0b00: // 00 is 'A'
                    builder.append('A');
                    break;
                case 0b01: // 01 is 'C'
                    builder.append('C');
                    break;
                case 0b10: // 10 is 'G'
                    builder.append('G');
                    break;
                case 0b11: // 11 is 'T'
                    builder.append('T');
                    break;
            }
        }
        return builder.toString();
    }

    public static void main(String[] args) {
        final String original = RandomStringUtils.random(20,0,4,true,false,'A','C','G','T');
        System.out.println(original);
        CompressedGene compressed = new CompressedGene(original);
        final String decompressed = compressed.decompress();
        System.out.println("decompressed: "+decompressed);
        System.out.println("orginal is the same as decompressed: "+decompressed.equalsIgnoreCase(original));
    }
}
