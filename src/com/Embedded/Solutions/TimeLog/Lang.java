/*******************************************************************************
 *                                                                             *
 *                                  Time Log                                   *
 *                                                                             *
 *                                  Lang.java                                  *
 *                                                                             *
 *                                   Module                                    *
 *                                                                             *
 *                    Copyright (C) 2010 Ulrik Hørlyk Hjort                    *
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


public class Lang {

	public final static int DK = 0;
	public final static int ENG = 1;
	public final static int GER = 2;
	public final static int FRA = 3;

	// private final static int LANG = DK;

	public final static int OK = 0;
	public final static int YES = 1;
	public final static int NO = 2;
	public final static int NEW = 3;
	public final static int LIST = 4;
	public final static int SEND = 5;
	public final static int HELP = 6;
	public final static int PRIVATE = 7;
	public final static int BUSINESS = 8;
	public final static int DESCRIPTION = 9;
	public final static int TRIP_START = 10;
	public final static int TRIP_STOP = 11;
	public final static int START = 12;
	public final static int FILLIN_TRIP_STOP = 13;
	public final static int FILLIN_TRIP_START = 14;
	public final static int TRIP_STOP_GT_TRIP_START = 15;
	public final static int NO_SEMICOLON = 16;
	public final static int STOP = 17;
	public final static int FILE_HEADLINE = 18;
	public final static int DELETE_MESSAGE = 19;
	public final static int CANCEL = 20;
	public final static int EDIT = 21;
	public final static int DELETE = 22;
	public final static int DELETE_ALL = 23;
	public final static int RECORDING_STARTED = 24;
	public final static int CONTINUE = 25;
	public final static int FROM = 26;
	public final static int TO = 27;
	public final static int DELETE_RANGE = 28;
	public final static int UPDATE = 29;
	public final static int NO_DATA_TO_SEND = 30;
	public final static int DEMO_VERSION_MESSAGE = 31;
	public final static int DEMO = 32;
	public final static int FULL = 33;
	public final static int NO_EMPTY_ODOMETER_FIELDS = 34;
	public final static int MAX_RECORDS_IN_DEMO_VERSION = 35;
	public final static int CANNOT_SEND_DATA_WHILE_RECORDING = 36;

	private static String[] title = { "Køre Dagbog", "Drive Log" };

	private static String[][] texts = {
	// DK:
			{
					"Ok",
					"Ja",
					"Nej",
					"Ny rejse",
					"Liste",
					"Send",
					"Hjælp",
					"Privat",
					"Firma",
					"Beskrivelse",
					"Odometer Start",
					"Odometer Stop",
					"Start",
					"Odometer Stop skal udfyldes",
					"Odometer Start skal udfyldes",
					"Odometer slutværdi skal være større end startværdi",
					"Tekst må ikke indeholde ';'",
					"Stop",
					"Type:;Starttid:;Sluttid:;Odemeter Start:;Odemeter Slut:;Odemeter Diff:;Kommentar:;Firma:;Privat:\n",
					"Slet Besked?", "Annuller", "Editer", "Slet", "Slet Alle",
					"Optagelse startet: ", " fortsættes", "Fra:", "Til:",
					"Slet interval", "Opdater", "Ingen data at sende",
					"Funktionen er ikke tilgængelig i demo versionen.", "Demo",
					"", "Odometer felterne må ikke være tomme",
					"Der kan kun lagres 5 rejser i demo versionen", "Data kan ikke sendes før rejsen er afsluttet" },

			// ENG:
			// Drivers log
			{
					"Ok",
					"Yes",
					"No",
					"New",
					"List",
					"Send",
					"Help",
					"Private",
					"Business",
					"Description",
					"Odometer Start",
					"Odometer Stop",
					"Start",
					"Fill in Odometer stop",
					"Fill in Odometer start",
					"Odometer end value must be greater than start value",
					"Text may not contain ';'",
					"Stop",
					"Type:;Start time:;End time:;Odometer Start:;Odometer End:;Odemeter Diff:;Connemt:;Business:;Private:\n",
					"Delete Message?", "Cancel", "Edit", "Delete",
					"Delete All", "Recording started at: ", " will continue",
					"From:", "To:", "Delete range", "Update",
					"No data to send",
					"This function is not available in the demo version.",
					"Demo", "", "Odometer fields may not me empty",
					"There can only be 5 records in the demo version"," Cannot send data while trip is recording" },

			// GER:
			{ "Ok", "Ja", "Nein" },

			// FRA:
			{ "Ok", "Oui", "Non" } };

	public static final int START_BEFORE_END_DATE = 0;
	public static final int END_BEFORE_START_DATE = 1;

	private static String[][] errorMessages = {
	// DK:
			{ "Start dato skal være før slut dato",
					"Slut dato skal være efter start dato" },
			// ENG:
			{ "Start date must be before end date",
					"End date must be after start date" } };

	public static String get(int text) {
		return texts[Config.LANG][text];
	}

	public static String getHelpText(String versionName, int versionCode) {

		String version = versionName + " " + versionCode + " "
				+ Config.getVersion();

		String[] html = {
				"<html><body><center><h1>K&oslash;re Dagbog</h1></center><center><h3>Version: "
						+ version
						+ "</h3></center><h4>Ny Rejse</h4>Ny rejse registreres ved at udfylde odometer feltet med den aktuelle afl&aelig;sning af bilens kilometert&aelig;ller, v&aelig;lge om rejsen er forretning eller privat, samt indtaste en beskrivelse af rejsen og derefter klikke \"Start\". Bem&aelig;rk, at  rejsebeskrivelsen er frivillig mens odemeter v&aelig;rdien skal indtastes. Ved endt rejse indtastes ny afl&aelig;st odemeter v&aelig;rdi,derefter trykkes stop og rejsen lagres. N&aring;r rejsen er startet kan applikationen lukkes ned. N&aring;r en rejse startes skifter startknappen farve til r&oslash;d<h4>Liste</h4>Viser tidligere rejser. Private rejser vises med hvid tekst og forretnings rejser vises med blå tekst. Ved tryk p&aring; en rejse kan denne editeres eller slettes. Ved tryk p&aring; menuknappen fremkommer et valg hvor alle rejser kan slettes.<h4>Send</h4>Et dato interval af rejser kan v&aelig;lges ved at trykke på hhv. til og fra datoen og herefter sendes via epost eller bluetooth, som en semikolon separeret csv fil best&aring;ende af:<br><br><b> Rejsetype, Start tidspunkt, Slut tidspunkt, Odemeter Start v&aelig;rdi, Odemeter slut v&aelig;rdi, Odemeter difference, Kommentar</b><br><br> Forslag til forbedringer samt rapportering af fejl og problemer kan sendes til: <a href=\"mailto:embedded.solutions.dk@gmail.com?subject=[DrivingLog Feedback]\">Embedded Solutions DK</a> </body></html>",
				"<html><body><center><h1>Drive Log</h1></center><center><h3>Version: "
						+ version
						+ "</h3></center><h4>New</h4>Register a new trip by filling the odometer field with the actual reading from the car odometer, choose business or private trip, write a note describing the trip and press the start button. The note is optional and the odemeter start value is required. When the trip is finish enter the new odemeter value in the odemeter stop field and press the stop button. The recording trip will be saved if the application is closed and can be stopped when the application is open again.When a recording is started the button change color to red.<h4>List</h4>Shows all recorded trips. Private trips are shown in white and business trips are shonw in blue. By clicking on a trip you can edit it or delete it. By pressing the menu button you get the option to delete all recorded trips.<h4>Send</h4>You can send a range of recorded trips by choosing start date and end date by clicking on the date fields. The records can be send by email and bluetooth as an semicolon seperated csv file. The format of the csv file is:<br><br><b>Triptype, Start time, End time, Odemeter start value, Odemeter end value, Odemeter difference, Note</b><br><br> Suggestions, problems and bug reports can be send to: <a href=\"mailto:embedded.solutions.dk@gmail.com?subject=[DrivingLog Feedback]\">Embedded Solutions DK</a> </body></html>",
				"<html><body></body></html>", "<html><body></body></html>", };

		return html[Config.LANG];
	}

	public static String getErrorMessage(int message) {
		return errorMessages[Config.LANG][message];
	}

	public static String getTitle() {
		return title[Config.LANG];
	}

}
