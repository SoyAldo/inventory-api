package org.kayteam.inventoryapi.util;

import java.util.ArrayList;
import java.util.List;

public class InventoryUtil {


    public static List< Integer > getSlotsFromFormats( List< String > formats ) {

        List< Integer > result = new ArrayList<>();

        for ( String format : formats ) {

            for ( int slot : getSlotsFromFormat( format ) ) {

                if ( ! result.contains( slot ) )   result.add( slot );

            }

        }

        return result;

    }

    public static List<Integer> getSlotsFromFormat( String format ) {

        List<Integer> result = new ArrayList<>();

        try {

            if ( format.contains( "," ) ) {

                String[] miniFormats = format.split( "," );

                for ( String miniFormat : miniFormats ) {

                    if ( miniFormat.contains( "-" ) ) {

                        int start = Integer.parseInt( miniFormat.split( "-" )[0] );

                        int end = Integer.parseInt( miniFormat.split( "-" )[1] );

                        for ( int i = start ; i <= end ; i++ )   result.add( i );

                    } else {

                        int slot = Integer.parseInt( miniFormat );

                        result.add( slot );

                    }

                }

            } else if ( format.contains( "-" ) ) {

                int start = Integer.parseInt( format.split( "-" )[0] );

                int end = Integer.parseInt( format.split( "-" )[1] );

                for ( int i = start ; i <= end ; i++ )   result.add( i );

            } else {

                int slot = Integer.parseInt( format );

                result.add( slot );

            }

        } catch ( NumberFormatException | IndexOutOfBoundsException ignore ) {}

        return result;

    }

}