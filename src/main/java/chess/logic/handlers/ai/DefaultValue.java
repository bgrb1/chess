package chess.logic.handlers.ai;


public final class DefaultValue {
	
	public final static int PAWN = 10;
	public final static int ROOK = 50;
	public final static int BISHOP = 30;
	public final static int QUEEN = 90;
	public final static int KING = 900;
	public final static int KNIGHT = 30;
	
	public final static double [] [] KING_POSITION_ARRAY = new double []  []{ 
			{-3.0, -4.0, -4.0, -5.0, -5.0, -4.0, -4.0, -3.0},
			{ -3.0, -4.0, -4.0, -5.0, -5.0, -4.0, -4.0, -3.0},
			{ -3.0, -4.0, -4.0, -5.0, -5.0, -4.0, -4.0, -3.0},
			{ -3.0, -4.0, -4.0, -5.0, -5.0, -4.0, -4.0, -3.0},
			{ -2.0, -3.0, -3.0, -4.0, -4.0, -3.0, -3.0, -2.0},
			{ -1.0, -2.0, -2.0, -2.0, -2.0, -2.0, -2.0, -1.0},
			{  2.0,  2.0,  0.0,  0.0,  0.0,  0.0,  2.0,  2.0},
			{  2.0,  3.0,  1.0,  0.0,  0.0,  1.0,  3.0,  2.0}	
	};

	
	public final static double [] [] PAWN_POSITION_ARRAY = new double []  []{
        {0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0},
        {5.0,  5.0,  5.0,  5.0,  5.0,  5.0,  5.0,  5.0},
        {1.0,  1.0,  2.0,  3.0,  3.0,  2.0,  1.0,  1.0},
        {0.5,  0.5,  1.0,  2.5,  2.5,  1.0,  0.5,  0.5},
        {0.0,  0.0,  0.0,  2.0,  2.0,  0.0,  0.0,  0.0},
        {0.5, -0.5, -1.0,  0.0,  0.0, -1.0, -0.5,  0.5},
        {0.5,  1.0, 1.0,  -2.0, -2.0,  1.0,  1.0,  0.5},
        {0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0}
    };
    
    public final static double [] [] KNIGHT_POSITION_ARRAY = new double []  [] {
        {-5.0, -4.0, -3.0, -3.0, -3.0, -3.0, -4.0, -5.0},
        {-4.0, -2.0,  0.0,  0.0,  0.0,  0.0, -2.0, -4.0},
        {-3.0,  0.0,  1.0,  1.5,  1.5,  1.0,  0.0, -3.0},
        {-3.0,  0.5,  1.5,  2.0,  2.0,  1.5,  0.5, -3.0},
        {-3.0,  0.0,  1.5,  2.0,  2.0,  1.5,  0.0, -3.0},
        {-3.0,  0.5,  1.0,  1.5,  1.5,  1.0,  0.5, -3.0},
        {-4.0, -2.0,  0.0,  0.5,  0.5,  0.0, -2.0, -4.0},
        {-5.0, -4.0, -3.0, -3.0, -3.0, -3.0, -4.0, -5.0}
    };
    
    public final static double [] [] BISHOP_POSITION_ARRAY = new double []  [] {
    	{ -2.0, -1.0, -1.0, -1.0, -1.0, -1.0, -1.0, -2.0},
    	{ -1.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0, -1.0},
    	{ -1.0,  0.0,  0.5,  1.0,  1.0,  0.5,  0.0, -1.0},
    	{ -1.0,  0.5,  0.5,  1.0,  1.0,  0.5,  0.5, -1.0},
    	{ -1.0,  0.0,  1.0,  1.0,  1.0,  1.0,  0.0, -1.0},
    	{ -1.0,  1.0,  1.0,  1.0,  1.0,  1.0,  1.0, -1.0},
    	{ -1.0,  0.5,  0.0,  0.0,  0.0,  0.0,  0.5, -1.0},
    	{ -2.0, -1.0, -1.0, -1.0, -1.0, -1.0, -1.0, -2.0}
    };
    
    public final static double [] [] ROOK_POSITION_ARRAY = new double []  [] {
    	{  0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0},
    	{  0.5,  1.0,  1.0,  1.0,  1.0,  1.0,  1.0,  0.5},
    	{ -0.5,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0, -0.5},
    	{ -0.5,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0, -0.5},
    	{ -0.5,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0, -0.5},
    	{ -0.5,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0, -0.5},
    	{ -0.5,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0, -0.5},
    	{  0.0,   0.0, 0.0,  0.5,  0.5,  0.0,  0.0,  0.0}
    };
    
    public final static double [] [] QUEEN_POSITION_ARRAY = new double []  []{
    	{ -2.0, -1.0, -1.0, -0.5, -0.5, -1.0, -1.0, -2.0},
    	{ -1.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0, -1.0},
    	{ -1.0,  0.0,  0.5,  0.5,  0.5,  0.5,  0.0, -1.0},
    	{ -0.5,  0.0,  0.5,  0.5,  0.5,  0.5,  0.0, -0.5},
    	{  0.0,  0.0,  0.5,  0.5,  0.5,  0.5,  0.0, -0.5},
    	{ -1.0,  0.5,  0.5,  0.5,  0.5,  0.5,  0.0, -1.0},
    	{ -1.0,  0.0,  0.5,  0.0,  0.0,  0.0,  0.0, -1.0},
    	{ -2.0, -1.0, -1.0, -0.5, -0.5, -1.0, -1.0, -2.0}
    };

}
