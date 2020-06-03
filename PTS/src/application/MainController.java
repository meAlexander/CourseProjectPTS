package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import application.messages.FXOptionPane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import stories.MostActiveUsers;
import stories.MostViewedFiles;
import stories.SearchFileOrCourseByID;
import stories.SearchTopFilesByUserWithID;
import stories.ShowAllInformation;

public class MainController implements Initializable {
	@FXML
	AnchorPane center;

	@FXML
	AnchorPane top;

	@FXML
	BorderPane center2;

	@FXML
	TableView<Information> view;

	@FXML
	Button users;

	@FXML
	Button files;

	@FXML
	CheckBox searchID;

	@FXML
	CheckBox userSearch;

	@FXML
	Button showAllInformation;

	private ObservableList<Information> list = FXCollections.observableArrayList();

	private FilteredList<Information> filter = new FilteredList<>(list, b -> true);

	private SortedList<Information> sortedData = new SortedList<>(filter);

	private List<String> ids = new ArrayList<String>();

	private List<String> fileCourse = new ArrayList<String>();

	private String activeUserIp = "";

	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			openFile();
		} catch (Exception e) {
			e.printStackTrace();
		}

		TableColumn<Information, String> date = new TableColumn<>("Time");
		date.setCellValueFactory(new PropertyValueFactory<>("date"));

		TableColumn<Information, String> course = new TableColumn<>("Еvent contex");
		course.setCellValueFactory(new PropertyValueFactory<>("course"));

		TableColumn<Information, String> event = new TableColumn<>("Event Name");
		event.setCellValueFactory(new PropertyValueFactory<>("event"));

		TableColumn<Information, String> id = new TableColumn<>("Description");
		id.setCellValueFactory(new PropertyValueFactory<>("id"));

		TableColumn<Information, String> ip = new TableColumn<>("IP");
		ip.setCellValueFactory(new PropertyValueFactory<>("ip"));

		ip.setStyle("-fx-border-color:black;");

		view.getColumns().addAll(date, course, event, id, ip);

		sortedData.comparatorProperty().bind(view.comparatorProperty());
		view.setItems(sortedData);

		searchTopFilesByUser();
		showTopFiles();
		showInformation();
		searchCourseOrFile();
		showTopUsers();
	}

	protected void showInformation() {
		ShowAllInformation info = new ShowAllInformation(showAllInformation, view);
		info.showAllInformationButton();
	}
	
	protected void searchCourseOrFile() {
		SearchFileOrCourseByID info = new SearchFileOrCourseByID(searchID, filter);
		info.searchFileOrCourseByIdButton();
	}
	
	protected Information createInformation(String date, String course, String event, String id, String ip) {
		return new Information(date, course, event, id, ip);
	}

	protected void showTopFiles() {
		files.setOnAction(e -> {
			try {
				FXOptionPane.showInformation("Most viewed Files:",
						new MostViewedFiles(ids, list, fileCourse).getTopViewedFiles("File"));
			} catch (NumberFormatException | IOException e1) {
				e1.printStackTrace();
			}
		});
	}

	protected void showTopUsers() {
		users.setOnAction(e -> {
			if (activeUserIp.isEmpty()) {
				try {
					activeUserIp = new MostActiveUsers(list, ids).getIpByUserId();
				} catch (NumberFormatException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			FXOptionPane.showInformation("Most active Users", /* "Table size:" + list.size() + "\n" + */ activeUserIp);
		});
	}
	
	protected void searchTopFilesByUser() {
		SearchTopFilesByUserWithID topFilesByUser = new SearchTopFilesByUserWithID(list, ids, userSearch);
		topFilesByUser.SearchTopFilesByUserWithIDButton();
	}

	public static void printToFIle(String text) {
		try {
			PrintWriter pw = new PrintWriter(new File("User-Search.txt"));
			pw.println(text);
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	protected void openFile() throws FileNotFoundException, UnsupportedEncodingException {
		File fileDir = new File("logs_BCS37_20181103.csv");
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileDir), "Cp1251"));
		try {
			reader.readLine();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		String line;
		try {
			while ((line = reader.readLine()) != null) {
				String splitLine[] = line.split(",");

				String userID = regex(splitLine[5], "'([\\d]{1,5})'", 1);
				String courseID = regex(splitLine[5], "'([\\d]{1,5})'[.]", 1);

				if (!courseID.isEmpty() && !userID.isEmpty()
						&& (splitLine[4].contains("Course module viewed") || splitLine[4].contains("Course viewed"))) {

					list.add(createInformation(splitLine[0].replace("\"", "") + splitLine[1].replace("\"", ""),
							splitLine[2].replaceAll("(File|Course|Forum): ", ""), splitLine[4], splitLine[5],
							splitLine[splitLine.length - 1]));
					ids.add(userID + "," + courseID);
					fileCourse.add(splitLine[2]);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String regex(String rawData, String regex, int group) {
		String url = "";

		Pattern p2 = Pattern.compile(regex);
		Matcher m2 = p2.matcher(rawData);

		if (m2.find()) {
			url = (m2.group(group));
		}
		return url;
	}
}