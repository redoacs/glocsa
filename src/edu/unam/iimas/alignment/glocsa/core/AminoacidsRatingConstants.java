/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.unam.iimas.alignment.glocsa.core;

/**
 *
 * @author xaltonalli
 */
public class AminoacidsRatingConstants {
    //                                            0     1     2     3     4     5     6     7     8     9     10    11    12    13    14    15    16    17    18    19    20    21    22    23    24    25    26    27    28
    static public char[] supportedCharacters = { 'A' , 'C' , 'D' , 'E' , 'F' , 'G' , 'H' , 'I' , 'K' , 'L' , 'M' , 'N' , 'O' , 'P' , 'Q' , 'R' , 'S' , 'T' , 'U' , 'V' , 'W' , 'Y' , '*' , '-' , 'B' , 'J' , 'Z' , 'X' , '?'}; //If you change this, change also supportedCharactersGapPosition and supportedCharactersAnyPosition accordingly
    static public int supportedCharactersGapPosition = 23;
    static public int supportedCharactersAnyPosition = 27;
    static public int basicCharacters = 22;

    static public double[][] charactersWeightsMatrix =  {
	//	0	,	1	,	2	,	3	,	4	,	5	,	6	,	7	,	8	,	9	,	10	,	11	,	12	,	13	,	14	,	15	,	16	,	17	,	18	,	19	,	20	,	21	,	22	,	23	,	24	,	25	,	26	,	27
	//	A	,	C	,	D	,	E	,	F	,	G	,	H	,	I	,	K	,	L	,	M	,	N	,	O	,	P	,	Q	,	R	,	S	,	T	,	U	,	V	,	W	,	Y	,	*	,	-	,	B	,	J	,	Z	,	X
	{	1.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	1.0/22.0	}	,	//	Alanine         -	A
	{	0.0	,	1.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	1.0/22.0	}	,	//	Cysteine	-	C
	{	0.0	,	0.0	,	1.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.5	,	0.0	,	0.0	,	1.0/22.0	}	,	//	Aspartic_acid	-	D
	{	0.0	,	0.0	,	0.0	,	1.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.5	,	1.0/22.0	}	,	//	Glutamic_acid	-	E
	{	0.0	,	0.0	,	0.0	,	0.0	,	1.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	1.0/22.0	}	,	//	Phenylalanine	-	F
	{	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	1.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	1.0/22.0	}	,	//	Glycine         -	G
	{	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	1.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	1.0/22.0	}	,	//	Histidine	-	H
	{	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	1.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.5	,	0.0	,	1.0/22.0	}	,	//	Isoleucine	-	I
	{	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	1.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	1.0/22.0	}	,	//	Lysine          -	K
	{	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	1.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.5	,	0.0	,	1.0/22.0	}	,	//	Leucine         -	L
	{	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	1.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	1.0/22.0	}	,	//	Methionine	-	M
	{	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	1.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.5	,	0.0	,	0.0	,	1.0/22.0	}	,	//	Asparagine	-	N
	{	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	1.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	1.0/22.0	}	,	//	Pyrrolysine	-	O
	{	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	1.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	1.0/22.0	}	,	//	Proline         -	P
	{	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	1.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.5	,	1.0/22.0	}	,	//	Glutamine	-	Q
	{	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	1.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	1.0/22.0	}	,	//	Arginine	-	R
	{	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	1.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	1.0/22.0	}	,	//	Serine          -	S
	{	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	1.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	1.0/22.0	}	,	//	Threonine	-	T
	{	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	1.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	1.0/22.0	}	,	//	Selenocysteine	-	U
	{	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	1.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	1.0/22.0	}	,	//	Valine          -	V
	{	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	1.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	1.0/22.0	}	,	//	Tryptophan	-	W
	{	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	1.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	1.0/22.0	}	,	//	Tryosine	-	Y
	{	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	1.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0             }	,	//	Stop            -	*
	{	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	0.0	,	1.0	,	0.0	,	0.0	,	0.0	,	0.0             }		//	Gap             -	-

    };
}