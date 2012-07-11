package de.rretzbach.learningkeyboard.dao;

import java.util.ArrayList;
import java.util.List;

import de.rretzbach.learningkeyboard.ProbableWord;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class FollowersDAO {

	private final SQLiteDatabase db;

	public FollowersDAO(SQLiteDatabase sqLiteDatabase) {
		this.db = sqLiteDatabase;
	}

	public void merge(List<Follower> followers) {
		db.beginTransaction();
		try {

			for (Follower follower : followers) {
				if (follower.getLastword() == null
						|| follower.getFollower() == null) {
					continue;
				}

				Integer lastwordId = getOrCreateWordId(follower.getLastword());
				Integer followerId = getOrCreateWordId(follower.getFollower());
				Integer currentFrequency = getOrCreateFrequency(lastwordId,
						followerId);

				ContentValues values = new ContentValues();
				values.put("frequency",
						currentFrequency + follower.getFrequency());
				db.update(
						"followers",
						values,
						"lastword = ? and follower = ?",
						new String[] { lastwordId.toString(),
								followerId.toString() });
			}

			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}
	}

	private Integer getOrCreateFrequency(Integer lastwordId, Integer followerId) {
		Integer frequency = null;

		Cursor query = db.query("followers", new String[] { "frequency" },
				"lastword = ? and follower = ?",
				new String[] { lastwordId.toString(), followerId.toString() },
				null, null, null);
		if (query.getCount() <= 0) {
			ContentValues initialValues = new ContentValues();
			initialValues.put("lastword", lastwordId);
			initialValues.put("follower", followerId);
			initialValues.put("frequency", 0);
			db.insertWithOnConflict("followers", null, initialValues,
					SQLiteDatabase.CONFLICT_IGNORE);
			frequency = 0;
		} else {
			query.moveToFirst();
			frequency = query.getInt(0);
		}

		return frequency;
	}

	protected Integer getOrCreateWordId(String word) {
		Integer id = null;

		Cursor query = db.query("words", new String[] { "_id" }, "word = ?",
				new String[] { word }, null, null, null);
		if (query.getCount() <= 0) {
			ContentValues initialValues = new ContentValues();
			initialValues.put("word", word);
			id = Integer.valueOf((int) db.insertWithOnConflict("words", null,
					initialValues, SQLiteDatabase.CONFLICT_IGNORE));
		} else {
			query.moveToFirst();
			id = query.getInt(0);
		}

		return id;
	}

	public List<ProbableWord> find(String lastWord) {
		List<ProbableWord> res = null;
		Cursor query = db.rawQuery(
				"select fw.word, f.frequency from words lw inner join followers f on lw._id = f.lastword inner join words fw on f.follower = fw._id where lw.word = ?",
				new String[] { lastWord });
		if (query.getCount() > 0) {
			res = new ArrayList<ProbableWord>();
			while (query.moveToNext()) {
				res.add(new ProbableWord(query.getString(0), query.getInt(1)));
			}
		}
		return res;
	}

	public List<String> findPartialWord(String partialInput) {
		List<String> res = null;
		Cursor query = db.rawQuery(
				"select word from words where word like ?",
				new String[] { partialInput + "%" });
		if (query.getCount() > 0) {
			res = new ArrayList<String>();
			while (query.moveToNext()) {
				res.add(query.getString(0));
			}
		}
		return res;
	}

}
