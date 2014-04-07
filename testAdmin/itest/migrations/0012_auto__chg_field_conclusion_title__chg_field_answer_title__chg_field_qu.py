# -*- coding: utf-8 -*-
from south.utils import datetime_utils as datetime
from south.db import db
from south.v2 import SchemaMigration
from django.db import models


class Migration(SchemaMigration):

    def forwards(self, orm):

        # Changing field 'Conclusion.title'
        db.alter_column(u'itest_conclusion', 'title', self.gf('django.db.models.fields.CharField')(max_length=250, null=True))

        # Changing field 'Answer.title'
        db.alter_column(u'itest_answer', 'title', self.gf('django.db.models.fields.CharField')(max_length=250, null=True))

        # Changing field 'Question.title'
        db.alter_column(u'itest_question', 'title', self.gf('django.db.models.fields.CharField')(max_length=250, null=True))

        # Changing field 'Test.title'
        db.alter_column(u'itest_test', 'title', self.gf('django.db.models.fields.CharField')(max_length=250, null=True))

    def backwards(self, orm):

        # Changing field 'Conclusion.title'
        db.alter_column(u'itest_conclusion', 'title', self.gf('django.db.models.fields.CharField')(default='', max_length=250))

        # Changing field 'Answer.title'
        db.alter_column(u'itest_answer', 'title', self.gf('django.db.models.fields.CharField')(default=1, max_length=250))

        # Changing field 'Question.title'
        db.alter_column(u'itest_question', 'title', self.gf('django.db.models.fields.CharField')(default=1, max_length=250))

        # Changing field 'Test.title'
        db.alter_column(u'itest_test', 'title', self.gf('django.db.models.fields.CharField')(default='a', max_length=250))

    models = {
        'itest.answer': {
            'Meta': {'object_name': 'Answer'},
            'conclusion': ('django.db.models.fields.related.ForeignKey', [], {'blank': 'True', 'related_name': "'+'", 'null': 'True', 'to': "orm['itest.Conclusion']"}),
            'content': ('django.db.models.fields.TextField', [], {'max_length': '5850', 'null': 'True', 'blank': 'True'}),
            'create_at': ('django.db.models.fields.DateTimeField', [], {'default': 'datetime.datetime.now'}),
            u'id': ('django.db.models.fields.AutoField', [], {'primary_key': 'True'}),
            'jump': ('django.db.models.fields.related.ForeignKey', [], {'blank': 'True', 'related_name': "'+'", 'null': 'True', 'to': "orm['itest.Question']"}),
            'num': ('django.db.models.fields.IntegerField', [], {'null': 'True', 'blank': 'True'}),
            'question': ('django.db.models.fields.related.ForeignKey', [], {'related_name': "'answers'", 'to': "orm['itest.Question']"}),
            'summary': ('django.db.models.fields.CharField', [], {'max_length': '450', 'null': 'True', 'blank': 'True'}),
            'title': ('django.db.models.fields.CharField', [], {'max_length': '250', 'null': 'True', 'blank': 'True'})
        },
        'itest.conclusion': {
            'Meta': {'ordering': "['num']", 'object_name': 'Conclusion'},
            'content': ('django.db.models.fields.TextField', [], {'max_length': '5850', 'null': 'True', 'blank': 'True'}),
            'create_at': ('django.db.models.fields.DateTimeField', [], {'default': 'datetime.datetime.now'}),
            u'id': ('django.db.models.fields.AutoField', [], {'primary_key': 'True'}),
            'num': ('django.db.models.fields.IntegerField', [], {'null': 'True', 'blank': 'True'}),
            'summary': ('django.db.models.fields.CharField', [], {'max_length': '450', 'null': 'True', 'blank': 'True'}),
            'test': ('django.db.models.fields.related.ForeignKey', [], {'related_name': "'conclusions'", 'to': "orm['itest.Test']"}),
            'title': ('django.db.models.fields.CharField', [], {'max_length': '250', 'null': 'True', 'blank': 'True'})
        },
        'itest.question': {
            'Meta': {'ordering': "['num']", 'object_name': 'Question'},
            'content': ('django.db.models.fields.TextField', [], {'max_length': '5850', 'null': 'True', 'blank': 'True'}),
            'create_at': ('django.db.models.fields.DateTimeField', [], {'default': 'datetime.datetime.now'}),
            u'id': ('django.db.models.fields.AutoField', [], {'primary_key': 'True'}),
            'num': ('django.db.models.fields.IntegerField', [], {'null': 'True', 'blank': 'True'}),
            'summary': ('django.db.models.fields.CharField', [], {'max_length': '450', 'null': 'True', 'blank': 'True'}),
            'test': ('django.db.models.fields.related.ForeignKey', [], {'related_name': "'questions'", 'to': "orm['itest.Test']"}),
            'title': ('django.db.models.fields.CharField', [], {'max_length': '250', 'null': 'True', 'blank': 'True'})
        },
        'itest.tag': {
            'Meta': {'object_name': 'Tag'},
            u'id': ('django.db.models.fields.AutoField', [], {'primary_key': 'True'}),
            'word': ('django.db.models.fields.CharField', [], {'max_length': '35'})
        },
        'itest.test': {
            'Meta': {'ordering': "['num']", 'object_name': 'Test'},
            'content': ('django.db.models.fields.TextField', [], {'max_length': '5850', 'null': 'True', 'blank': 'True'}),
            'create_at': ('django.db.models.fields.DateTimeField', [], {'default': 'datetime.datetime.now'}),
            u'id': ('django.db.models.fields.AutoField', [], {'primary_key': 'True'}),
            'num': ('django.db.models.fields.IntegerField', [], {'null': 'True', 'blank': 'True'}),
            'summary': ('django.db.models.fields.CharField', [], {'max_length': '450', 'null': 'True', 'blank': 'True'}),
            'tags': ('django.db.models.fields.related.ManyToManyField', [], {'related_name': "'tests'", 'symmetrical': 'False', 'to': "orm['itest.Tag']"}),
            'title': ('django.db.models.fields.CharField', [], {'max_length': '250', 'null': 'True', 'blank': 'True'})
        }
    }

    complete_apps = ['itest']