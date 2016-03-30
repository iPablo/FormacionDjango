from django.forms import widgets
from rest_framework import serializers
from .models import NewsItem, Event
from django.utils import timezone

"""
class NewsItemSerializer(serializers.Serializer):
	pk = serializers.IntegerField(read_only=True)
	title = serializers.CharField(max_length=50)
	description = serializers.CharField(max_length=100)
	publish_date = serializers.DateTimeField(default=timezone.now)

	def create(self, validated_data):
		return NewsItem.objects.create(**validated_data)

	def update(self, instance, validated_data):
		instance.title = validated_data.get('title', instance.title)
		instance.description = validated_data.get('description', instance.description)
		instance.publish_date = validated_data.get('publish_date', instance.publish_date)
		instance.save()
		return instance

class EventSerializer(serializers.Serializer):
	pk = serializers.IntegerField(read_only=True)
	title = serializers.CharField(max_length=50)
	description = serializers.CharField(max_length=100)
	start_date = serializers.DateTimeField(blank=True, null=True)
	end_date = serializers.DateTimeField(blank=True, null=True)

	def create(self, validated_data):
		return Event.objects.create(**validated_data)

	def update(self, instance, validated_data):
		instance.title = validated_data.get('title', instance.title)
		instance.description = validated_data.get('description', instance.description)
		instance.start_date = validated_data.get('start_date', instance.start_date)
		instance.end_date = validated_data.get('end_date', instance.end_date)
		instance.save()
		return instance
"""

class NewsItemSerializer(serializers.ModelSerializer):
	
	class Meta:
		model = NewsItem
		fields = ('id', 'title', 'description', 'publish_date')

class EventSerializer(serializers.ModelSerializer):

	class Meta:
		model = Event
		fields = ('id', 'title', 'description', 'start_date', 'end_date')
		
			
			