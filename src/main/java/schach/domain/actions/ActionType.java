package schach.domain.actions;

public enum ActionType {
	
	INVALID, // unzulässig, regelwidrig
	NORMAL_MOVE, // Normaler Zug ohne Besonderheiten
	CAPTURE, // Schlag
	CASTLING, // Rochade
	PROMOTION, // Bauernumwandlung
	PROMOTION_AND_CAPTURE, //Bauernumwandlung, bei der der Bauer beim Zug auf die gegnerische Grundlinie eine andere Figur schlägt
	EN_PASSANT; // Bauer schlägt anderen Bauern im vorbeigehen

}
