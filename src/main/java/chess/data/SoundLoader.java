package chess.data;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip; 
public class SoundLoader {
	
	public synchronized static void playHitSound() {
	      try {
	    	  ClassLoader loader = Thread.currentThread().getContextClassLoader();
	          Clip clip = AudioSystem.getClip();
	          AudioInputStream audio = AudioSystem.getAudioInputStream(loader.getResourceAsStream("resources/hit.wav"));
	          clip.open(audio);
	          clip.start(); 
	        } catch (Exception e) {
	          e.printStackTrace();
	        }
	}
	
	public synchronized static void playMoveSound() {
	      try {
	  		  ClassLoader loader = Thread.currentThread().getContextClassLoader();
	          Clip clip = AudioSystem.getClip();
	          AudioInputStream audio = AudioSystem.getAudioInputStream(loader.getResourceAsStream("resources/move.wav"));
	          clip.open(audio);
	          clip.start(); 
	        } catch (Exception e) {
	          e.printStackTrace();
	        }
	}
	
	//private synchronized void play(Clip clip)

}
