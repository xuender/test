# -*- coding: utf-8 -*-
from south.utils import datetime_utils as datetime
from south.db import db
from south.v2 import SchemaMigration
from django.db import models


class Migration(SchemaMigration):

    def forwards(self, orm):
        # Adding model 'Conclusion'
        db.create_table(u'itest_conclusion', (
            (u'id', self.gf('django.db.models.fields.AutoField')(primary_key=True)),
            ('create_at', self.gf('django.db.models.fields.DateTimeField')(default=datetime.datetime.now)),
            ('num', self.gf('django.db.models.fields.IntegerField')(null=True, blank=True)),
            ('title', self.gf('django.db.models.fields.CharField')(max_length=150)),
            ('summary', self.gf('django.db.models.fields.CharField')(max_length=450, null=True, blank=True)),
            ('content', self.gf('django.db.models.fields.TextField')(max_length=5850, null=True, blank=True)),
        ))
        db.send_create_signal('itest', ['Conclusion'])


        # Changing field 'Test.content'
        db.alter_column(u'itest_test', 'content', self.gf('django.db.models.fields.TextField')(max_length=5850, null=True))

    def backwards(self, orm):
        # Deleting model 'Conclusion'
        db.delete_table(u'itest_conclusion')


        # Changing field 'Test.content'
        db.alter_column(u'itest_test', 'content', self.gf('django.db.models.fields.CharField')(max_length=5850, null=True))

    models = {
        'itest.conclusion': {
            'Meta': {'object_name': 'Conclusion'},
            'content': ('django.db.models.fields.TextField', [], {'max_length': '5850', 'null': 'True', 'blank': 'True'}),
            'create_at': ('django.db.models.fields.DateTimeField', [], {'default': 'datetime.datetime.now'}),
            u'id': ('django.db.models.fields.AutoField', [], {'primary_key': 'True'}),
            'num': ('django.db.models.fields.IntegerField', [], {'null': 'True', 'blank': 'True'}),
            'summary': ('django.db.models.fields.CharField', [], {'max_length': '450', 'null': 'True', 'blank': 'True'}),
            'title': ('django.db.models.fields.CharField', [], {'max_length': '150'})
        },
        'itest.tag': {
            'Meta': {'object_name': 'Tag'},
            u'id': ('django.db.models.fields.AutoField', [], {'primary_key': 'True'}),
            'word': ('django.db.models.fields.CharField', [], {'max_length': '35'})
        },
        'itest.test': {
            'Meta': {'object_name': 'Test'},
            'content': ('django.db.models.fields.TextField', [], {'max_length': '5850', 'null': 'True', 'blank': 'True'}),
            'create_at': ('django.db.models.fields.DateTimeField', [], {'default': 'datetime.datetime.now'}),
            u'id': ('django.db.models.fields.AutoField', [], {'primary_key': 'True'}),
            'num': ('django.db.models.fields.IntegerField', [], {'null': 'True', 'blank': 'True'}),
            'summary': ('django.db.models.fields.CharField', [], {'max_length': '450', 'null': 'True', 'blank': 'True'}),
            'tags': ('django.db.models.fields.related.ManyToManyField', [], {'related_name': "'tests'", 'symmetrical': 'False', 'to': "orm['itest.Tag']"}),
            'title': ('django.db.models.fields.CharField', [], {'max_length': '150'})
        }
    }

    complete_apps = ['itest']