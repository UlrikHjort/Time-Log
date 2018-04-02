/*******************************************************************************
 *                                                                             *
 *                                  Time Log                                   *
 *                                                                             *
 *                                  Item.java                                  *
 *                                                                             *
 *                                   Module                                    *
 *                                                                             *
 *                    Copyright (C) 2010 Ulrik H¿rlyk Hjort                    *
 *                                                                             *
 *   Time Log is free software;  you can  redistribute it                      *
 *   and/or modify it under terms of the  GNU General Public License           *
 *   as published  by the Free Software  Foundation;  either version 2,        *
 *   or (at your option) any later version.                                    *
 *   Time Log is distributed in the hope that it will be                       *
 *   useful, but WITHOUT ANY WARRANTY;  without even the  implied warranty     *
 *   of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.                   *
 *   See the GNU General Public License for  more details.                     *
 *   You should have  received  a copy of the GNU General                      *
 *   Public License  distributed with Yolk.  If not, write  to  the  Free      *
 *   Software Foundation,  51  Franklin  Street,  Fifth  Floor, Boston,        *
 *   MA 02110 - 1301, USA.                                                     *
 *                                                                             *
 ******************************************************************************/
package com.Embedded.Solutions.TimeLog;

public class Item {
	
	private long m_id;
	private String m_type;
	private long  m_startTime;
	private long m_endTime;
	private long m_tripStart;
	private long m_tripEnd;
	private String m_message;
	
	
	
	
	
	
	public Item(String type, String message) {
		m_type = type;
        m_startTime = System.currentTimeMillis();
        m_message = message;
	  
	}

	
	public Item(long id, String type, String message, long startTime, long endTime) {
		m_id = id;
		m_type = type;
        m_startTime = System.currentTimeMillis();
        m_message = message;
        m_startTime = startTime;
        m_endTime = endTime;
	  
	}
	
	
	

	/**
	 * @return the m_id
	 */
	public long getId() {
		return m_id;
	}


	/**
	 * @return the m_type
	 */
	public String getType() {
		return m_type;
	}


	/**
	 * @param type the m_type to set
	 */
	public void setType(String type) {
		m_type = type;
	}


	/**
	 * @return the m_startTime
	 */
	public String getStartTime() {
		return DateTimeFormat.getTime(m_startTime);
	}

	public long getStartTimeLong() {
		return m_startTime;
	}

	/**
	 * @param startTime the m_startTime to set
	 */
	public void setStartTime(long startTime) {
		m_startTime = startTime;
	}


	public String getEndTimeString() {
		return DateTimeFormat.getTime(m_endTime);
	}
	
	
	/**
	 * @return the m_endTime
	 */
	public long getEndTime() {
		return m_endTime;
	}


	/**
	 * @param endTime the m_endTime to set
	 */
	public void setEndTime(long endTime) {
		m_endTime = endTime;
	}


	/**
	 * @return the m_message
	 */
	public String getMessage() {
		return m_message;
	}


	/**
	 * @param message the m_message to set
	 */
	public void setMessage(String message) {
		m_message = message;
	}
	
	public void stopItem() {
		m_endTime = System.currentTimeMillis();
	}

}	
