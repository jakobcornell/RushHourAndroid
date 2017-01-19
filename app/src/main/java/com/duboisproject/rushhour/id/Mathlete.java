/*
 * Dubois Traffic Puzzle
 * Jakob Cornell, 2017
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package com.duboisproject.rushhour.id;

import android.os.Parcel;

public final class Mathlete implements DuboisIdentity {
	public final String id;
	public final String firstName, lastName;

	public Mathlete(String id, String firstName, String lastName) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	private int mData;
	/*
	private Mathlete(Parcel in) {
		mData = in.readInt();
	}
	*/

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeInt(mData);
	}

	public static final Creator<Mathlete> CREATOR = new Creator<Mathlete>() {
		@Override
		public Mathlete createFromParcel(Parcel in) {
			return null;//new Mathlete(in);
		}

		@Override
		public Mathlete[] newArray(int size) {
			return new Mathlete[size];
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}
}
