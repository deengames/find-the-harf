package com.deengames.radiantwrench.utils;

import java.util.Comparator;

import com.deengames.radiantwrench.view.Drawable;
import com.deengames.radiantwrench.view.ImageButton;
import com.deengames.radiantwrench.view.ImageCheckbox;
import com.deengames.radiantwrench.view.Sprite;
import com.deengames.radiantwrench.view.SpriteSheet;
import com.deengames.radiantwrench.view.Text;

public class ZTypeOrderComparator implements Comparator<Drawable> {

	@Override
	// Sort backwards; we want the array Zs to be arranged lowest to highest.
    // This means that the lowest-Z items are drawn first, under the rest.
	public int compare(Drawable d1, Drawable d2) {
		if (d1.getZ() != d2.getZ()) {
			return compareInts(d1.getZ(), d2.getZ());
		} else {
			if (d1.getClass() != d2.getClass()) {
				return compareTypes(d1, d2);
			} else {
				return compareInts(d1.getOrderAdded(), d2.getOrderAdded());
			}
		}
	} 
	
	// Bloody Java, doesn't implement ints as a class with compareTo :(
	// See C# implementation of CompareTo
	private int compareInts(int i1, int i2) {
		if (i1 < i2) {
			return -1;
		} else if (i2 < i1) {
			return 1;
		} else {
			return 0;
		}
	}
	
	private int compareTypes(Drawable d1, Drawable d2) {
		int d1TypePriority = getDrawPriority(d1);
		int d2TypePriority = getDrawPriority(d2);
		return compareInts(d1TypePriority, d2TypePriority);
	}
	

    // Higher number = higher priority = draw on top
    private int getDrawPriority(Drawable d)
    {
        if (d instanceof ImageCheckbox){
            return 5;
        } else if (d instanceof ImageButton) {
            return 4;
        } else if (d instanceof Text) {
        	return 3;
        } else if (d instanceof SpriteSheet) {
            return 2;
        } else if (d instanceof Sprite) {
            return 1;
        } else {
        	throw new RadiantWrenchException("Not sure what the draw priority is for a " + d.getClass().getName());
        }
    }


}
