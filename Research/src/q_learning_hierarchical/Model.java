package q_learning_hierarchical;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;


//モデルは2箇所のjointを持つ紐みたいなのを想定。付け根、先端、ジョイント2箇所がxy方向に稼働可能な8自由度モデル
final class Model {
	//初期位置
	private static final Point DEFAULT_POSITION = new Point(50, 100.0);
	private static final int JOINT_COUNT = 2; //関節の数
	
	private final ObjectProperty<Point> basePosition; //指の付け根の位置
	private final ObjectProperty<Point> tickPosition; //指の先端の位置
	private final List<ObjectProperty<Point>> jointPositionList; //第一関節と第二関節。番号が若いほうが根本に近い
	
	Model() {
		this.basePosition = new SimpleObjectProperty<>(DEFAULT_POSITION);
		this.jointPositionList = new ArrayList<>();
		for (int index = 0; index < JOINT_COUNT; index++) {
			this.jointPositionList.add(new SimpleObjectProperty<>(DEFAULT_POSITION));
		}
		this.tickPosition = new SimpleObjectProperty<>(DEFAULT_POSITION);
	}
	
	final Point getBasePosition() {
		return this.basePosition.get();
	}
	
	final void setBasePosition(Point position) {
		this.basePosition.set(position);
	}
	
	final List<ObjectProperty<Point>> getJointPositionList() {
		return this.jointPositionList;
	}
	
	final Point getTickPosition() {
		return this.tickPosition.get();
	}
	
	final void setTickPosition(Point position) {
		this.tickPosition.set(position);
	}

	final void init() {
		this.basePosition.set(DEFAULT_POSITION);
		this.tickPosition.set(DEFAULT_POSITION);
		for (ObjectProperty<Point> jointPosition : this.jointPositionList) {
			jointPosition.set(DEFAULT_POSITION);
		}
	}
}