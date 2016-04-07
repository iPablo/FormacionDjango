from django.forms import widgets
from rest_framework import serializers
from base.models import NewsItem, Event


class NewsItemSerializer(serializers.ModelSerializer):
    class Meta:
        model = NewsItem
        fields = ('id', 'title', 'description', 'publish_date')


class EventSerializer(serializers.ModelSerializer):
    class Meta:
        model = Event
        fields = ('id', 'title', 'description', 'start_date', 'end_date')
