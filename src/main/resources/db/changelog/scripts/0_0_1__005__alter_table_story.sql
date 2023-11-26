ALTER TABLE story ADD COLUMN total_chapter int NULL DEFAULT 0;
UPDATE story set total_chapter = (select count(id) from chapter where chapter.story_id = story.id);
