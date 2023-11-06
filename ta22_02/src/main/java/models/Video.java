package models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Video {

	private int id;
	private String title; 
	private String director;
	private int cli_id;
	
	public Video(String title, String director, int cli_id) {
		this.title = title;
		this.director = director;
		this.cli_id = cli_id;
	}

	@Override
	public String toString() {
		return "Video " + id + "\n" + title + "\n Director: " + director + "\n cli_id: " + cli_id ;
	}
	
	
}
