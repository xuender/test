# -*- coding: utf-8 -*-
from south.utils import datetime_utils as datetime
from south.db import db
from south.v2 import SchemaMigration
from django.db import models


class Migration(SchemaMigration):

    def forwards(self, orm):
        # Adding model 'Answer'
        db.create_table(u'itest_answer', (
            (u'id', self.gf('django.db.models.fields.AutoField')(primary_key=True)),
            ('create_at', self.gf('django.db.models.fields.DateTimeField')(default=datetime.datetime.now)),
            ('num', self.gf('django.db.models.fields.IntegerField')(null=True, blank=True)),
            ('title', self.gf('django.db.models.fields.CharField')(max_length=250)),
            ('summary', self.gf('django.db.models.fields.CharField')(max_length=450, null=True, blank=True)),
            ('content', self.gf('django.db.models.fields.TextField')(max_length=5850, null=True, blank=True)),
            ('question', self.gf('django.db.models.fields.related.ForeignKey')(related_name='answers', to=orm['itest.Question'])),
            ('jump', self.gf('django.db.models.fields.related.ForeignKey')(blank=True, related_name='+', null=True, to=orm['itest.Question'])),
            ('conclusion', self.gf('django.db.models.fields.related.ForeignKey')(blank=True, related_name='+', null=True, to=orm['itest.Conclusion'])),
        ))
        db.send_create_signal('itest', ['Answer'])

        # Adding model 'Question'
        db.create_table(u'itest_question', (
            (u'id', self.gf('django.db.models.fields.AutoField')(primary_key=True)),
            ('create_at', self.gf('django.db.models.fields.DateTimeField')(default=datetime.datetime.now)),
            ('num', self.gf('django.db.models.fields.IntegerField')(null=True, blank=True)),
            ('title', self.gf('django.db.models.fields.CharField')(max_length=250)),
            ('summary', self.gf('django.db.models.fields.CharField')(max_length=450, null=True, blank=True)),
            ('content', self.gf('django.db.models.fields.TextField')(max_length=5850, null=True, blank=True)),
            ('test', self.gf('django.db.models.fields.related.ForeignKey')(related_name='questions', to=orm['itest.Test'])),
        ))
        db.send_create_signal('itest', ['Question'])


    def backwards(self, orm):
        # Deleting model 'Answer'
        db.delete_table(u'itest_answer')

        # Deleting model 'Question'
        db.delete_table(u'itest_question')


    models = {
        'itest.answer': {
            'Meta': {'ordering': "['num']", 'object_name': 'Answer'},
            'conclusion': ('django.db.models.fields.related.ForeignKey', [], {'blank': 'True', 'related_name': "'+'", 'null': 'True', 'to': "orm['itest.Conclusion']"}),
            'content': ('django.db.models.fields.TextField', [], {'max_length': '5850', 'null': 'True', 'blank': 'True'}),
            'create_at': ('django.db.models.fields.DateTimeField', [], {'default': 'datetime.datetime.now'}),
            u'id': ('django.db.models.fields.AutoField', [], {'primary_key': 'True'}),
            'jump': ('django.db.models.fields.related.ForeignKey', [], {'blank': 'True', 'related_name': "'+'", 'null': 'True', 'to': "orm['itest.Question']"}),
            'num': ('django.db.models.fields.IntegerField', [], {'null': 'True', 'blank': 'True'}),
            'question': ('django.db.models.fields.related.ForeignKey', [], {'related_name': "'answers'", 'to': "orm['itest.Question']"}),
            'summary': ('django.db.models.fields.CharField', [], {'max_length': '450', 'null': 'True', 'blank': 'True'}),
            'title': ('django.db.models.fields.CharField', [], {'max_length': '250'})
        },
        'itest.conclusion': {
            'Meta': {'ordering': "['num']", 'object_name': 'Conclusion'},
            'content': ('django.db.models.fields.TextField', [], {'max_length': '5850', 'null': 'True', 'blank': 'True'}),
            'create_at': ('django.db.models.fields.DateTimeField', [], {'default': 'datetime.datetime.now'}),
            u'id': ('django.db.models.fields.AutoField', [], {'primary_key': 'True'}),
            'num': ('django.db.models.fields.IntegerField', [], {'null': 'True', 'blank': 'True'}),
            'summary': ('django.db.models.fields.CharField', [], {'max_length': '450', 'null': 'True', 'blank': 'True'}),
            'test': ('django.db.models.fields.related.ForeignKey', [], {'related_name': "'conclusions'", 'to': "orm['itest.Test']"}),
            'title': ('django.db.models.fields.CharField', [], {'max_length': '250'})
        },
        'itest.question': {
            'Meta': {'ordering': "['num']", 'object_name': 'Question'},
            'content': ('django.db.models.fields.TextField', [], {'max_length': '5850', 'null': 'True', 'blank': 'True'}),
            'create_at': ('django.db.models.fields.DateTimeField', [], {'default': 'datetime.datetime.now'}),
            u'id': ('django.db.models.fields.AutoField', [], {'primary_key': 'True'}),
            'num': ('django.db.models.fields.IntegerField', [], {'null': 'True', 'blank': 'True'}),
            'summary': ('django.db.models.fields.CharField', [], {'max_length': '450', 'null': 'True', 'blank': 'True'}),
            'test': ('django.db.models.fields.related.ForeignKey', [], {'related_name': "'questions'", 'to': "orm['itest.Test']"}),
            'title': ('django.db.models.fields.CharField', [], {'max_length': '250'})
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
            'title': ('django.db.models.fields.CharField', [], {'max_length': '250'})
        }
    }

    complete_apps = ['itest']