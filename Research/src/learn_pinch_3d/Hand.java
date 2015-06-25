package learn_pinch_3d;





//手のモデル。ただし　親指と人差指だけで、操作できる関節はとりあえず根本1ヶ所
final class Hand {
	final ThumbFinger thumbFinger;
	final IndexFinger indexFinger;

	Hand() {
		this.thumbFinger = new ThumbFinger();
		this.indexFinger = new IndexFinger();
		this.reset();
	}

	final void reset() {
		this.thumbFinger.reset();
		this.indexFinger.reset();
	}
}
