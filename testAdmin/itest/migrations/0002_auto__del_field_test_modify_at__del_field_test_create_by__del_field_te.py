# -*- coding: utf-8 -*-
from south.utils import datetime_utils as datetime
from south.db import db
from south.v2 import SchemaMigration
from django.db import models


class Migration(SchemaMigration):

    def forwards(self, orm):
        # Deleting field 'Test.modify_at'
        db.delete_column(u'itest_test', 'modify_at')

        # Deleting field 'Test.create_by'
        db.delete_column(u'itest_test', 'create_by_id')

        # Deleting field 'Test.modify_by'
        db.delete_column(u'itest_test', 'modify_by_id')


    def backwards(self, orm):
        # Adding field 'Test.modify_at'
        db.add_column(u'itest_test', 'modify_at',
                      self.gf('django.db.models.fields.DateTimeField')(default=datetime.datetime.now),
                      keep_default=False)

        # Adding field 'Test.create_by'
        db.add_column(u'itest_test', 'create_by',
                      self.gf('django.db.models.fields.related.ForeignKey')(default=1, related_name='+', to=orm['auth.User']),
                      keep_default=False)

        # Adding field 'Test.modify_by'
        db.add_column(u'itest_test', 'modify_by',
                      self.gf('django.db.models.fields.related.ForeignKey')(default=1, related_name='+', to=orm['auth.User']),
                      keep_default=False)


    models = {
        'itest.tag': {
            'Meta': {'object_name': 'Tag'},
            u'id': ('django.db.models.fields.AutoField', [], {'primary_key': 'True'}),
            'word': ('django.db.models.fields.CharField', [], {'max_length': '35'})
        },
        'itest.test': {
            'Meta': {'object_name': 'Test'},
            'content': ('django.db.models.fields.CharField', [], {'max_length': '250', 'null': 'True', 'blank': 'True'}),
            'create_at': ('django.db.models.fields.DateTimeField', [], {'default': 'datetime.datetime.now'}),
            u'id': ('django.db.models.fields.AutoField', [], {'primary_key': 'True'}),
            'num': ('django.db.models.fields.IntegerField', [], {'null': 'True', 'blank': 'True'}),
            'summary': ('django.db.models.fields.CharField', [], {'max_length': '250', 'null': 'True', 'blank': 'True'}),
            'tags': ('django.db.models.fields.related.ManyToManyField', [], {'related_name': "'tests'", 'symmetrical': 'False', 'to': "orm['itest.Tag']"}),
            'title': ('django.db.models.fields.CharField', [], {'max_length': '150'})
        }
    }

    complete_apps = ['itest']